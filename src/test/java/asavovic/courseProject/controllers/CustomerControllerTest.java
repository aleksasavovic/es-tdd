package asavovic.courseProject.controllers;

import asavovic.courseProject.entities.Customer;
import asavovic.courseProject.services.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .build();
    }

    @Test
    void registrationSuccess() throws Exception {
        String email = "existing@email.com";
        String password = "123";
        Customer customer = new Customer(email, password);

        when(customerService.register(customer)).thenReturn(new Customer(email, password));

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}")
                        .contentType("application/json"))
                .andExpect(status().isOk());
        verify(customerService, times(1)).register(any());
    }

    @Test
    public void registerExistingUser() throws Exception {
        String email = "existing@email.com";
        String password = "123";
        Customer customer = new Customer(email, password);

        when(customerService.register(customer)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}")
                        .contentType("application/json"))
                .andExpect(status().isConflict());
        verify(customerService, times(1)).register(any());
    }

    @Test
    public void login() throws Exception {
        String email = "existing@email.com";
        String password = "123";
        Long sessionId = 1L;

        when(customerService.login(email, password)).thenReturn(sessionId);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        long response = Long.parseLong(content);

        Assertions.assertEquals(response, sessionId);

    }

    @Test
    public void loginWrongCredentials() throws Exception {
        String email = "existing@email.com";
        String password = "123";
        Long sessionId = 1L;

        when(customerService.login(email, password)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}")
                        .contentType("application/json"))
                .andExpect(status().isUnauthorized());

    }

}

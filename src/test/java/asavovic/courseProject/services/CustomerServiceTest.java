package asavovic.courseProject.services;

import asavovic.courseProject.entities.Customer;
import asavovic.courseProject.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    CustomerService customerService;
    private static final String EMAIL = "aleska@gmail.com";
    private static final String PASSWORD = "password";
    private static final String ENCRYPTED_PASSWORD = "pa$$word";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testRegisterWithNewCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setEmail(EMAIL);
        newCustomer.setPassword(PASSWORD);


        when(customerRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(customerRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        when(passwordEncoder.encode(eq(PASSWORD))).thenReturn(ENCRYPTED_PASSWORD);


        Customer savedCustomer = customerService.register(newCustomer);

        verify(customerRepository, times(1)).save(any());

        assertEquals(EMAIL, savedCustomer.getEmail());
        assertEquals(ENCRYPTED_PASSWORD, savedCustomer.getPassword());
    }

    @Test
    public void testRegisterWithExistingEmail() {
        String email = EMAIL;
        String password = PASSWORD;
        Customer newCustomer = new Customer();
        newCustomer.setEmail(email);
        newCustomer.setPassword(password);
        Customer existingCustomer = new Customer();
        existingCustomer.setEmail(email);

        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(existingCustomer));

        Customer savedCustomer = customerService.register(newCustomer);

        assertNull(savedCustomer);

    }

}

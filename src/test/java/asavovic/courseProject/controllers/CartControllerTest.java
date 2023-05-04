package asavovic.courseProject.controllers;

import asavovic.courseProject.entities.dto.ProductToAdd;
import asavovic.courseProject.exceptions.DeficientResourcesException;
import asavovic.courseProject.services.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CartControllerTest {
    @Mock
    CartService cartService;

    @InjectMocks
    CartController cartController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(cartController)
                .build();
    }

    @Test
    public void addProductToCart() throws Exception {
        Long id = 1L;
        Long amountToAdd = 5L;
        ProductToAdd product = new ProductToAdd(id,amountToAdd);

        mockMvc.perform(MockMvcRequestBuilders.post("/addProduct")
                        .content("{\"id\":\"" + id + "\",\"amountToAdd\":\"" + amountToAdd + "\"}")
                        .contentType("application/json"))
                .andExpect(status().isOk());

        verify(cartService, times(1)).addProductToCart(any());
    }

    @Test
    public void addProductToCartNotEnoguhItemsInStore() throws Exception {
        Long id = 1L;
        Long amountToAdd = 5L;
        ProductToAdd product = new ProductToAdd(id,amountToAdd);
        when(cartService.addProductToCart(product)).thenThrow(DeficientResourcesException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/addProduct")
                        .content("{\"id\":\"" + id + "\",\"amountToAdd\":\"" + amountToAdd + "\"}")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());

    }


}

package asavovic.courseProject.controllers;

import asavovic.courseProject.entities.Cart;
import asavovic.courseProject.entities.dto.CartDTO;
import asavovic.courseProject.entities.dto.ProductToAdd;
import asavovic.courseProject.exceptions.DeficientResourcesException;
import asavovic.courseProject.services.CartService;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;


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
        ProductToAdd product = new ProductToAdd();
        product.setId(id);
        product.setAmountToAdd(amountToAdd);


        mockMvc.perform(MockMvcRequestBuilders.post("/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("sessionId", 2L)
                        .content("{ \"id\": \"" + id + "\", \"amountToAdd\": " + amountToAdd + " }"))
                .andExpect(status().isOk());


        verify(cartService, times(1)).addProductToCart(product, 2L);
    }

    @Test
    public void addProductToCartNotEnoguhItemsInStore() throws Exception {
        Long id = 1L;
        Long amountToAdd = 5L;
        ProductToAdd product = new ProductToAdd(id, amountToAdd);
        when(cartService.addProductToCart(product, 2L)).thenThrow(DeficientResourcesException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("sessionId", 2L)
                        .content("{ \"id\": \"" + id + "\", \"amountToAdd\": " + amountToAdd + " }"))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void showCart() throws Exception {
        Long sessionId = 1L;
        CartDTO cartDTO = new CartDTO();
        when(cartService.showCart(sessionId)).thenReturn(cartDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cart")
                        .header("sessionId", sessionId))
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        CartDTO cartResult = mapper.readValue(responseBody, CartDTO.class);

        Assertions.assertEquals(cartDTO.getTotalPrice(), cartResult.getTotalPrice());
        Assertions.assertIterableEquals(cartDTO.getProducts(), cartResult.getProducts());

    }

    @Test
    void removeProductFromCart() throws Exception {
        mockMvc.perform(post("/cart/removeProduct")
                        .header("sessionId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 2}"))
                .andExpect(status().isOk());

        verify(cartService, times(1)).removeProductFromCart(1L, 2L);
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Long sessionId = 1L;
        Long productId = 2L;
        ProductToAdd productDTO = new ProductToAdd();
        productDTO.setId(productId);
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/updateProduct")
                        .header("sessionId", sessionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": "+productId+" }"))
                .andExpect(status().isOk());

        verify(cartService, times(1)).updateQuantityOfProductsInCart(productDTO, sessionId);
    }

    @Test
    void testCheckout() throws Exception {
        mockMvc.perform(post("/cart/checkout")
                        .header("sessionId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, times(1)).checkout(1L);
    }
}

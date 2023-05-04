package asavovic.courseProject.controllers;

import asavovic.courseProject.entities.Customer;
import asavovic.courseProject.entities.Product;
import asavovic.courseProject.services.CustomerService;
import asavovic.courseProject.services.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .build();
    }

    @Test
    void getAllProductsFromStore() throws Exception {
        Product product1 = new Product(1L, "Milk", 8L, 200,new HashSet<>());
        Product product2 = new Product(2L, "Eggs", 100L, 25, new HashSet<>());
        Set<Product> products = new HashSet<>();
        products.add(product1);
        products.add(product2);

        when(productService.getAllProducts()).thenReturn(products);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Set<Product> response = new ObjectMapper().readValue(content, new TypeReference<Set<Product>>() {
        });
        assertEquals(2, response.size());
        assertEquals(products, response);


    }

}

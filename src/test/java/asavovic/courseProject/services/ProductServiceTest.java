package asavovic.courseProject.services;

import asavovic.courseProject.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllProductsNoProduct() {
        Product product1 = new Product(1L, "Milk", 8L, 200);
        Product product2 = new Product(2L, "Eggs", 100L, 25);
        List<Product> products = new ArrayList<>(Arrays.asList(product2, product1));

        when(productRepository.findAll()).thenReturn(products);

        Set<Product> expected = new HashSet<>();
        expected.add(product1);
        expected.add(product2);


        assertEquals(expected, productService.getAllProducts());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getAllProductsNoProduct() {
        List<Product> products = new ArrayList<>();

        when(productRepository.findAll()).thenReturn(products);

        Set<Product> expected = new HashSet<>();


        assertEquals(expected, productService.getAllProducts());

        verify(productRepository, times(1)).findAll();
    }

}

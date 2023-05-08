package asavovic.courseProject.services;

import asavovic.courseProject.entities.CartProduct;
import asavovic.courseProject.entities.Product;
import asavovic.courseProject.repositories.ProductRepository;
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
    void getAllProducts() {
        Product product1 = new Product(1L, "Milk", 8L, 200,new HashSet<>());
        Product product2 = new Product(2L, "Eggs", 100L, 25, new HashSet<>());
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

    @Test
    void updateAvailableQuantities() {
        CartProduct cartProduct1 = new CartProduct();
        CartProduct cartProduct2 = new CartProduct();
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(2L);
        cartProduct1.setProduct(product1);
        cartProduct2.setProduct(product2);
        cartProduct2.setQuantity(10L);
        cartProduct1.setQuantity(15L);

        Set<CartProduct> productsInCart = new HashSet<>();
        productsInCart.add(cartProduct1);
        productsInCart.add(cartProduct2);

        productService.updateAvailableQuantities(productsInCart,1);

        verify(productRepository,times(2)).updateAmountById(any(),any());
    }


}

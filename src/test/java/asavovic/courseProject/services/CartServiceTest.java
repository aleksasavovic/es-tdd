package asavovic.courseProject.services;

import asavovic.courseProject.entities.*;
import asavovic.courseProject.entities.dto.ProductToAdd;
import asavovic.courseProject.exceptions.DeficientResourcesException;
import asavovic.courseProject.repositories.CartProductRepository;
import asavovic.courseProject.repositories.CartRepository;
import asavovic.courseProject.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class CartServiceTest {
    @Mock
    ProductRepository productRepository;

    @Mock
    CartRepository cartRepository;

    @Mock
    SessionService sessionService;
    @Mock
    CartProductRepository cartProductRepository;

    @Mock
    ProductService productService;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addProduct() {
        Long sessionId = 1L;
        Long productId = 2L;
        Product product1 = new Product(1L, "Eggs", 10L, 100, new HashSet<>());
        Cart cart = new Cart();
        cart.setId(3L);
        Session session = new Session(sessionId, new Customer(), cart);
        ProductToAdd productToAdd = new ProductToAdd();
        productToAdd.setId(productId);
        productToAdd.setAmountToAdd(2L);

        when(sessionService.getSessionById(any())).thenReturn(session);
        when(productService.getProductById(any())).thenReturn(product1);
        when(cartProductRepository.findById(any())).thenReturn(Optional.empty());

        cartService.addProductToCart(productToAdd, sessionId);
        CartProduct cartProduct = cart.getProducts()
                .stream()
                .findFirst()
                .get();

        assertEquals(cartProduct.getProduct(), product1);
        verify(cartProductRepository).save(cartProduct);
        verify(cartRepository).save(cart);
        assertEquals(cartProduct.getQuantity(), 2L);
    }

    @Test
    void addProductWithExistingCartProductId() {
        Long sessionId = 1L;
        Long productId = 2L;
        Product product1 = new Product(1L, "Eggs", 10L, 100, new HashSet<>());
        Cart cart = new Cart();
        cart.setId(3L);
        Session session = new Session(sessionId, new Customer(), cart);
        ProductToAdd productToAdd = new ProductToAdd();
        productToAdd.setId(productId);
        productToAdd.setAmountToAdd(2L);

        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct(product1);
        cartProduct.setQuantity(4L);


        when(sessionService.getSessionById(any())).thenReturn(session);
        when(productService.getProductById(any())).thenReturn(product1);
        when(cartProductRepository.findById(any())).thenReturn(Optional.of(cartProduct));

        cartService.addProductToCart(productToAdd, sessionId);
        CartProduct updatedCartProduct = cart.getProducts()
                .stream()
                .findFirst()
                .get();
        assertEquals(updatedCartProduct.getQuantity(), 6L);
        assertEquals(updatedCartProduct.getProduct(), product1);
        verify(cartProductRepository).save(cartProduct);
        verify(cartRepository).save(cart);
    }

    @Test
    void addProductStoreDoesntHaveEnoguhItems() {
        Long sessionId = 1L;
        Long productId = 2L;
        Product product1 = new Product(1L, "Eggs", 10L, 100, new HashSet<>());
        Cart cart = new Cart();
        cart.setId(3L);
        Session session = new Session(sessionId, new Customer(), cart);
        ProductToAdd productToAdd = new ProductToAdd();
        productToAdd.setId(productId);
        productToAdd.setAmountToAdd(12L);

        when(sessionService.getSessionById(any())).thenReturn(session);
        when(productService.getProductById(any())).thenReturn(product1);
        when(cartProductRepository.findById(any())).thenReturn(Optional.empty());


        assertThrows(DeficientResourcesException.class, () -> cartService.addProductToCart(productToAdd, sessionId));


        verify(cartProductRepository, never()).save(any());
        verify(cartRepository, never()).save(any());

    }


}

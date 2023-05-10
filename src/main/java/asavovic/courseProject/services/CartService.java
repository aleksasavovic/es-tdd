package asavovic.courseProject.services;

import asavovic.courseProject.entities.*;
import asavovic.courseProject.entities.dto.CartDTO;
import asavovic.courseProject.entities.dto.ProductDisplay;
import asavovic.courseProject.entities.dto.ProductToAdd;
import asavovic.courseProject.exceptions.DeficientResourcesException;
import asavovic.courseProject.exceptions.EmptyCartException;
import asavovic.courseProject.exceptions.ResourceNotFoundException;
import asavovic.courseProject.repositories.CartProductRepository;
import asavovic.courseProject.repositories.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CartService {
    private final SessionService sessionService;
    private final ProductService productService;

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    public CartService(SessionService sessionService, ProductService productService, CartRepository cartRepository, CartProductRepository cartProductRepository) {
        this.sessionService = sessionService;
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
    }

    @Transactional
    public Void addProductToCart(ProductToAdd productToAdd, Long sessionId) {
        Session session = sessionService.getSessionById(sessionId);
        Product product = productService.getProductById(productToAdd.getId());


        Cart cart = session.getCart();

        CartProductId cartProductId = new CartProductId();
        cartProductId.setProductId(product.getId());
        cartProductId.setCartId(cart.getId());


        CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElse(new CartProduct(cartProductId, cart, product, 0L));

        cartProduct.setQuantity(cartProduct.getQuantity() + productToAdd.getAmountToAdd());
        if (cartProduct.getQuantity() > product.getQuantity())
            throw new DeficientResourcesException("store doesn't have that many items");
        cart.getProducts().add(cartProduct);
        cartProductRepository.save(cartProduct);
        cartRepository.save(cart);
        return null;
    }

    public CartDTO showCart(Long sessionId) {
        Session session = sessionService.getSessionById(sessionId);
        Cart cart = session.getCart();
        Set<CartProduct> productsInCart = cart.getProducts();
        List<ProductDisplay> displays = new ArrayList<>();
        int index = 0;
        long totalPrice = 0L;
        for (CartProduct cartProduct : productsInCart) {
            long subTotalPrice = cartProduct.getQuantity() * cartProduct.getProduct().getPrice();
            totalPrice += subTotalPrice;
            displays.add(new ProductDisplay(index++, cartProduct.getProduct().getName(), cartProduct.getQuantity(), subTotalPrice));
        }

        return new CartDTO(displays, totalPrice);

    }

    public void removeProductFromCart(Long sessionId, Long productId) {
        Session session = sessionService.getSessionById(sessionId);
        Cart cart = session.getCart();
        CartProductId cartProductId = new CartProductId();
        cartProductId.setProductId(productId);
        cartProductId.setCartId(cart.getId());

        checkIfProductIsInCart(cartProductId);
        cartProductRepository.deleteById(cartProductId);
    }

    private boolean checkIfProductIsInCart(CartProductId cartProductId) {
        cartProductRepository.findById(cartProductId)
                .orElseThrow(() -> new ResourceNotFoundException("product with id: " + cartProductId.getProductId() + " not found in the cart"));
        return true;
    }

    @Transactional
    public boolean updateQuantityOfProductsInCart(ProductToAdd productDTO, Long sessionId) {
        Session session = sessionService.getSessionById(sessionId);

        Cart cart = session.getCart();

        CartProductId cartProductId = new CartProductId();
        cartProductId.setProductId(productDTO.getId());
        cartProductId.setCartId(cart.getId());

        Product product = productService.getProductById(productDTO.getId());

        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(() -> new ResourceNotFoundException("you don't have product with id :" + productDTO.getId()));


        if (productDTO.getAmountToAdd() > product.getQuantity())
            throw new DeficientResourcesException("not enough items with id : " + productDTO.getId());

        cartProduct.setQuantity(productDTO.getAmountToAdd());
        cartProductRepository.save(cartProduct);
        return true;
    }

    @Transactional
    public void checkout(Long sessionId) {
        Session session = sessionService.getSessionById(sessionId);
        Customer customer = session.getCustomer();
        Cart cart = session.getCart();

        if (cart.getProducts().size() == 0)
            throw new EmptyCartException("ur cart is empty");

        checkAvailabilityAndPrice(cart.getProducts());

        productService.updateAvailableQuantities(cart.getProducts(), 1);

        cart.setSession(null);
        session.setCart(new Cart());
    }

    private void checkAvailabilityAndPrice(Set<CartProduct> products) {
        for (CartProduct product : products) {
            productService.checkAvailableQuantityAndPrice(product.getProduct().getId(), product.getQuantity(), product.getProduct().getPrice());
        }
    }
}

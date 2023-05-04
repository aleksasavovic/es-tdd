package asavovic.courseProject.services;

import asavovic.courseProject.entities.*;
import asavovic.courseProject.entities.dto.CartDTO;
import asavovic.courseProject.entities.dto.ProductToAdd;
import asavovic.courseProject.exceptions.DeficientResourcesException;
import asavovic.courseProject.repositories.CartProductRepository;
import asavovic.courseProject.repositories.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
        return null;
    }
}

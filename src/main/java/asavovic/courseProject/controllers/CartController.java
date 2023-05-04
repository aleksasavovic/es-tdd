package asavovic.courseProject.controllers;

import asavovic.courseProject.entities.dto.CartDTO;
import asavovic.courseProject.entities.dto.ProductToAdd;
import asavovic.courseProject.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Void> addProductToCart(@RequestBody ProductToAdd productToBuy, @RequestHeader Long sessionId) {
        cartService.addProductToCart(productToBuy, sessionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/cart")
    public CartDTO getCart(@RequestHeader Long sessionId) {
        return cartService.showCart(sessionId);
    }

    @PostMapping("/cart/removeProduct")
    public void removeProduct(@RequestBody ProductToAdd productDTO, @RequestHeader Long sessionId) {
        cartService.removeProductFromCart(sessionId, productDTO.getId());
    }

    @PostMapping("/cart/updateProduct")
    public void updateProduct(@RequestBody ProductToAdd productDTO, @RequestHeader Long sessionId) {
        cartService.updateQuantityOfProductsInCart(productDTO, sessionId);
    }
}

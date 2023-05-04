package asavovic.courseProject.controllers;

import asavovic.courseProject.entities.dto.ProductToAdd;
import asavovic.courseProject.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Void> addProductToCart(@RequestBody ProductToAdd productToBuy){
        cartService.addProductToCart(productToBuy);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

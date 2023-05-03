package asavovic.courseProject.controllers;

import asavovic.courseProject.entities.Product;
import asavovic.courseProject.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public Set<Product> getAllProducts() {
        return this.productService.getAllProducts();
    }
}

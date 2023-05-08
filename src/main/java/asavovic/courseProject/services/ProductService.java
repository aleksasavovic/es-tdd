package asavovic.courseProject.services;

import asavovic.courseProject.entities.CartProduct;
import asavovic.courseProject.entities.Product;
import asavovic.courseProject.exceptions.ResourceNotFoundException;
import asavovic.courseProject.exceptions.ServerErrorException;
import asavovic.courseProject.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Set<Product> getAllProducts() {
        Set<Product> products = new HashSet<>();
        productRepository.findAll().iterator().forEachRemaining(products::add);
        return products;
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("no such product"));
    }
    public void updateAvailableQuantities(Set<CartProduct> productsInCart, int sign) {
        for (CartProduct cartProduct : productsInCart) {
            productRepository.updateAmountById(sign * cartProduct.getQuantity(), cartProduct.getProduct().getId());
        }
    }

    public Long getAvailableQuantity(Long productId) {
        return productRepository.findQuantityById(productId).orElseThrow(()-> new ServerErrorException("server error"));
    }
}

package asavovic.courseProject.services;

import asavovic.courseProject.entities.Product;
import asavovic.courseProject.repositories.ProductRepository;
import org.apache.catalina.util.ErrorPageSupport;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public Set<Product> getAllProducts() {
        Set<Product> products = new HashSet<>();
        productRepository.findAll().iterator().forEachRemaining(products::add);
        return products;
    }
}

package asavovic.courseProject.repositories;

import asavovic.courseProject.entities.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    public void testUpdateAmountById() {
        Product product = new Product();
        product.setId(1L);
        product.setQuantity(10L);

        productRepository.save(product);

        int i = productRepository.updateAmountById(3L, 1L);
        productRepository.flush();

        Product updatedProduct = productRepository.findById(1L).orElse(null);

        assertNotNull(updatedProduct);
        assertEquals(Long.valueOf(7L), updatedProduct.getQuantity());
    }
    @Test
    @Transactional
    public void testFindQuantityById() {
        Product product = new Product();
        product.setId(1L);
        product.setQuantity(10L);

        productRepository.save(product);

        Long quantity = productRepository.findQuantityById(1L).orElse(null);

        assertNotNull(quantity);
        assertEquals(Long.valueOf(10L), quantity);
    }
}
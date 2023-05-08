package asavovic.courseProject.repositories;

import asavovic.courseProject.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Product set quantity = quantity - :amount where id = :id ")
    Integer updateAmountById(@Param("amount") Long amount, @Param("id") Long id);

    @Query("SELECT p.quantity FROM Product p WHERE p.id = :productId")
    Optional<Long> findQuantityById(@Param("productId") Long productId);
}

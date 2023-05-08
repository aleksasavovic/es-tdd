package asavovic.courseProject.repositories;

import asavovic.courseProject.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Product set quantity = quantity - :amount where id = :id ")
    Integer updateAmountById(@Param("amount") Long amount, @Param("id") Long id);
}

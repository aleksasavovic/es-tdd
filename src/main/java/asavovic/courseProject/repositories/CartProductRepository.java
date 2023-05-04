package asavovic.courseProject.repositories;

import asavovic.courseProject.entities.CartProduct;
import asavovic.courseProject.entities.CartProductId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartProductRepository extends JpaRepository<CartProduct, CartProductId> {


}

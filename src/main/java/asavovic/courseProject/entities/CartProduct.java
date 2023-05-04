package asavovic.courseProject.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class CartProduct {
    @EmbeddedId
    private CartProductId cartProductId;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private Long quantity;

    public CartProduct(CartProductId cartProductId, Cart cart, Product product, Long quantity) {
        this.cartProductId = cartProductId;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public CartProduct() {
    }
}

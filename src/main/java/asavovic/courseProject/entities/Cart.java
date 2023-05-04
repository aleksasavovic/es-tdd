package asavovic.courseProject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = "session")
@ToString(exclude = "session")
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @JsonIgnore
    @OneToOne(mappedBy = "cart")
    private Session session;
    @JsonIgnore
    @OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE)
    private Set<CartProduct> products = new HashSet<>();


}

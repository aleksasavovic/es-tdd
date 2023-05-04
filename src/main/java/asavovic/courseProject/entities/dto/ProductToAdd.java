package asavovic.courseProject.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductToAdd {
    private Long id;
    private Long amountToAdd;
}

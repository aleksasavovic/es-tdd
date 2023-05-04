package asavovic.courseProject.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDisplay {
    private int ordinal;
    private String name;
    private Long amount;

    private Long subTotalPrice;


}

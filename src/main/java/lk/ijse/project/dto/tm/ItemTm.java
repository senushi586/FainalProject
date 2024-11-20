package lk.ijse.project.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemTm {
    private String id;
    private String name;
    private String category;
    private String brand;
    private Double unitPrice;
    private int qty;
}

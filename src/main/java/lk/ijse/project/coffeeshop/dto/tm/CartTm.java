package lk.ijse.project.coffeeshop.dto.tm;

import javafx.scene.control.Button;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Data

public class CartTm {
    private String code;
    private String description;
    private int qty;
    private double unitPrice;
    private double tot;
    private Button removebtn;
}

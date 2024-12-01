package lk.ijse.project.coffeeshop.dto;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto {
    private String id;
    private String name;
    private String category;
    private String brand;
    private double unitPrice;
    private int qty;
}

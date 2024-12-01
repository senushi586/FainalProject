package lk.ijse.project.coffeeshop.dto;

import lk.ijse.project.coffeeshop.dto.tm.CartTm;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrdersDto {
    private String id;
    private String c_id;
    private LocalDate date;
    private Double payment;
    private List<CartTm> tmList = new ArrayList<>();

}

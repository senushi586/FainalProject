package lk.ijse.project.coffeeshop.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomerTm {
    private String id;
    private String name;
    private String address;
    private String mobile;
    private String email;
    private String dob;
}

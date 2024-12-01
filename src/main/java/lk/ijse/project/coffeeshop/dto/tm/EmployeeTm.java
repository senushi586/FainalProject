package lk.ijse.project.coffeeshop.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EmployeeTm {
    private String id;
    private String name;
    private String role;
    private String mobile;
    private String email;

}

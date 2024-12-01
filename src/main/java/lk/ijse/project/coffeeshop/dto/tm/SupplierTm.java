package lk.ijse.project.coffeeshop.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierTm {
    private String id;
    private String company_name;
    private String location;
    private String mobile;
    private String email;

}

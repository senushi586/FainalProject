package lk.ijse.project.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class BookingDto {
    private String id;
    private String c_id;
    private String nic;
    private String date;
    private String table_name;

}

package lk.ijse.project.dto;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String username;
    private String password;
    private String title;
    private String e_mail;
}

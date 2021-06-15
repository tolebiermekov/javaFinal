package kz.edu.astanait.ajp2_final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserApiDTO {
    private Long id;
    private String username;
    private String password;
}

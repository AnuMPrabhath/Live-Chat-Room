package dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString

public class UserDTO {
    private String username;
    private String password;
    private int status;
}

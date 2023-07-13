package entity;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString

public class User {
    private String username;
    private String password;
    private int status;
}

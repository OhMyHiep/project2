package entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Integer user_id;
    private String username;
    private String passwd;
    private String firstname;
    private String lastname;
    private String authToken;

}
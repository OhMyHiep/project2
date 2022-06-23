package entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private String user_id;
    private String username;
    private String passwd;
    private String firstname;
    private String lastname;
    private String authToken;



}

package entity;


import io.javalin.core.security.RouteRole;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role implements RouteRole {

    private int role_id;
    private String role_title;

}

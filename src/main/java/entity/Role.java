package entity;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    private int role_id;
    private String role_title;

}

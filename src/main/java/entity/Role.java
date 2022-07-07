package entity;


import io.javalin.core.security.RouteRole;
import lombok.*;


@Generated
@Data
public class Role {

    private Integer role_id;
    private RouteRole role_title;

    @Generated
    public enum RoleTitle implements RouteRole {
        Programmer,
        TechLead,
        ProgrammerAndTechLead,
        Everyone;
    };

    public Role(int id, String roletitle) {
        if (roletitle.toLowerCase().equals("programmer")) {
            role_title = RoleTitle.Programmer;
        } else if (roletitle.toLowerCase().equals("techlead")) {
            role_title = RoleTitle.TechLead;
        } else if (roletitle.toLowerCase().equals("everyone")) {
            role_title = RoleTitle.Everyone;
        } else if (roletitle.toLowerCase().equals("programmerandtechlead")) {
            role_title = RoleTitle.ProgrammerAndTechLead;
        }

        role_id = id;
    }

    public String getRole_title(){
        return role_title+"";
    }

}

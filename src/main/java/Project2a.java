import entity.Role;
import entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import service.AuthService;
import utils.Tuple;

import java.util.ArrayList;

public class Project2a {

    public static void main(String[] args) {
        ArrayList<Role> roles = new ArrayList<>();
        User usr = new User(1,"lol","lol","ha","ha","");

        Role testRole= new Role(1,"programmer");
        Role testRole2 = new Role(2,"TechLead");

        roles.add(testRole);
        roles.add(testRole2);

        Tuple<ArrayList<Role>,User> tp = new Tuple<>(roles,usr);

        AuthService as = new AuthService();

        String token = as.buildJwt(tp);
        System.out.println(token);

        Jws<Claims> jc = as.decryptJwt(token);

        Tuple<ArrayList<Role>,User> tpFinal = as.decodeJwt(jc);
        System.out.println(tpFinal.x.size()==0);
        for (Role rr : tpFinal.x) {
            System.out.println(rr.getRole_title());
            System.out.println("this ran");
        }
        System.out.println(tpFinal.y.toString());
        System.out.println(as.buildJwt(tpFinal));
    }
}

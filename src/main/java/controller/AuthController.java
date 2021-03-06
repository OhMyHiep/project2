package controller;

import domain.repsonse.AuthResponse;
import domain.request.AuthRequest;
import entity.Role;
import entity.User;
import service.AuthService;
import utils.Tuple;
import io.javalin.http.Handler;
import java.util.ArrayList;


public class AuthController {

    private static AuthService authService=new AuthService();

    public static ArrayList<Role> ddJwt(String jwtString){
        Tuple<ArrayList<Role>, User> roleContainer=authService.ddJwt(jwtString);
        return roleContainer.x;
    }
    public static Handler login = ctx -> {
        AuthRequest login= ctx.bodyAsClass(AuthRequest.class);
        if (login != null) {
            AuthResponse authResponse = authService.buildJwt(login);
            if (authResponse == null) {
                ctx.result("No such user exists");
                ctx.status(404);
            }
            else {
                ctx.json(authResponse);
            }
        }
        else {
            ctx.result("Invalid login json");
            ctx.status(400);
        }
    };

}

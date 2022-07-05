package controller;

import domain.repsonse.BugResponse;
import io.javalin.http.Handler;
import service.UserService;

public class UserController {

    private static UserService userService= new UserService();

    public static Handler getAll= ctx-> {
        ctx.json(userService.getAll());
    };

    
}

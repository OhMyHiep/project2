
import controller.AuthController;
import controller.BugCommentController;
import controller.UserController;
import entity.Role;
import io.javalin.Javalin;
import controller.BugController;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinPebble;

import java.util.ArrayList;
import java.util.List;


public class Project2 {
    public static void main(String[] args) {
        JavalinRenderer.register(JavalinPebble.INSTANCE,".html");
        Javalin app = Javalin.create(
                config -> {
                    config.addStaticFiles("/public", Location.CLASSPATH);
                    config.accessManager((handler, ctx, routeRoles) -> {

                        List<Role> userRole;

                        if (ctx.header("Authorization") != null) {
                            userRole = AuthController.ddJwt(ctx.header("Authorization"));
                        } else {
                            userRole = new ArrayList<>();
                            userRole.add(new Role(3, "everyone"));
                        }

                        boolean flag = false;
                        for (Role r: userRole) {
                            if (routeRoles.contains(r.getRole_title())) {
                                flag = true;
                                break;
                            }
                        }
                        if ( flag /*routeRoles.contains(userRole)*/) {
                            handler.handle(ctx);
                        } else {
                            ctx.status(401).result("Unauthorized");
                        }

                    });
                }

        );

        app.start();
        app.get("/comments/comment/{commentId}", BugCommentController.getCommentByIdRequest,Role.RoleTitle.Programmer);

        app.delete("/bug/{bug_id}",BugController.deleteBugById,Role.RoleTitle.Programmer);
        app.get("/bug/{bug_id}", BugController.getBugById, Role.RoleTitle.Programmer);
        app.get("/bug",BugController.bugList,Role.RoleTitle.Programmer);
        app.post("/bug",BugController.addBug,Role.RoleTitle.Programmer);
        app.patch("/bug",BugController.updateBug,Role.RoleTitle.Programmer);
        app.get("/bug/assign/{assignedTo}",BugController.bugsByAssignee,Role.RoleTitle.Programmer);

        app.get("/comments/{bug_id}",BugCommentController.viewAllCommentsRequest,Role.RoleTitle.Programmer);
        app.post("/comments",BugCommentController.createCommentRequest,Role.RoleTitle.Programmer);


        app.get("/user", UserController.getAll,Role.RoleTitle.Programmer);
        app.post("/login", AuthController.login, Role.RoleTitle.Everyone);

        htmlRoutes(app);
    }

    private static void htmlRoutes(Javalin app){
        app.get("/bugSubmission",ctx->{ctx.render("bugSubmission.html");
        }, Role.RoleTitle.Everyone);
        app.get("/detailBug",ctx->{ctx.render("bug.html");
        }, Role.RoleTitle.Everyone);
        app.get("/",ctx->{ctx.render("login.html");
        }, Role.RoleTitle.Everyone);
        app.get("/view",ctx->{ctx.render("bugView.html");
        }, Role.RoleTitle.Everyone);
//        app.get("/managerReviewBug",ctx->{ctx.render("managerReviewBug.html");
//        },Role.RoleTitle.TechLead);

    }

}

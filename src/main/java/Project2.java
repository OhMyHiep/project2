
import controller.BugCommentController;
import io.javalin.Javalin;
import controller.BugController;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinPebble;


public class Project2 {
    public static void main(String[] args) {
        JavalinRenderer.register(JavalinPebble.INSTANCE,".html");
        Javalin app = Javalin.create(
                config -> {
                    config.addStaticFiles("/public", Location.CLASSPATH);
                }
        );
        app.start();

        app.get("/", ctx->{
            ctx.render("bug.html");
        }, Role.RoleTitle.Programmer);
        app.get("bug/{bug_id}/comments", BugCommentController.viewAllCommentsRequest,Role.RoleTitle.Programmer);
        app.post("bug/{bug_id}/comments", BugCommentController.createCommentRequest,Role.RoleTitle.Programmer);
        app.get("/comments/comment/{commentId}", BugCommentController.getCommentByIdRequest,Role.RoleTitle.Programmer);

        app.get("/view", ctx->{
            ctx.render("bugView.html");
        });

        app.delete("/bug/{bug_id}",BugController.deleteBugById,Role.RoleTitle.Programmer);
        app.get("/bug/{bug_id}", BugController.getBugById, Role.RoleTitle.Programmer);
        app.get("/bug",BugController.bugList,Role.RoleTitle.Programmer);
        app.post("/bug",BugController.addBug,Role.RoleTitle.Programmer);
        app.patch("/bug",BugController.updateBug,Role.RoleTitle.Programmer);

        app.get("/comments/{bug_id}",BugCommentController.viewAllCommentsRequest);
        app.post("/comments",BugCommentController.createCommentRequest);
        app.get("/bug/assign/{assignedTo}",BugController.bugsByAssignee);
        htmlRoutes(app);
    }

    private static void htmlRoutes(Javalin app){
        app.get("/bugSubmission",ctx->{ctx.render("bugSubmission.html");
        });
        app.get("/detailBug",ctx->{ctx.render("bug.html");
        });
        app.get("/login",ctx->{ctx.render("login.html");
        });
        app.get("/bugView",ctx->{ctx.render("bugView.html");
        });
        app.get("/managerReviewBug",ctx->{ctx.render("managerReviewBug.html");
        });

    }

}

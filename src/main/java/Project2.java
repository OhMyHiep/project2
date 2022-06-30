
import controller.BugCommentController;
import io.javalin.Javalin;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import controller.BugController;
import dao.BugCommentDao;
import entity.Bug;
import entity.BugComment;
import dao.BugDaoImpl;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinPebble;
import service.BugService;
import java.sql.Date;


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
            ctx.render("login.html");
        });

        app.get("/view", ctx->{
            ctx.render("bugView.html");
        });
//        app.delete("/bug/:bug_id",BugController.deleteBugById);
//        app.get("/bug/:bug_id", BugController.getBugById);

        app.get("/bug",BugController.bugList);
        app.post("/bug",BugController.addBug);
        app.patch("/bug",BugController.updateBug);
        app.get("/comments/{bug_id}",BugCommentController.viewAllCommentsRequest);
        app.post("/comments",BugCommentController.createCommentRequest);
    }
}

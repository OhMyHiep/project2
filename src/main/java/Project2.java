
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
        Javalin app = Javalin.create().start(9090);

        // later will be bug/{bug_id}/comments
        app.post("/comments", BugCommentController.viewAllCommentsRequest);
        app.post("/comments/new-comment", BugCommentController.createCommentRequest);
        BugDaoImpl bugDao= new BugDaoImpl();
        BugService bugService=new BugService();
//        System.out.println( bugDao.deleteById(8));

        System.out.println(bugService.getById(100));

//        System.out.println(bugService.update(Bug.builder()
//                        .bug_id(5)
//                        .description("testing updating bug 5 description replacing")
//                .creator_id(1)
//                .issueDate(new Date(new java.util.Date().getTime()))
//                .build()));
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
        app.delete("/bug/:bug_id",BugController.deleteBugById);
        app.get("/bug/:bug_id", BugController.getBugById);
        app.get("/bug",BugController.bugList);
        app.post("/bug",BugController.addBug);
        app.patch("/bug",BugController.updateBug);

    }
}

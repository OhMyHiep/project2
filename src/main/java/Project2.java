<<<<<<< HEAD
import dao.BugCommentDao;
import entity.BugComment;
import entity.User;

public class Project2 {
    public static void main(String[] args){
//        User user=User.builder().
//                firstname("Hiep").
//                lastname("Huynh").
//                build();
//        System.out.println(user.getFirstname());
        BugCommentDao bc = new BugCommentDao();
        BugComment test = bc.getById(1);
        System.out.println(test);
=======
import com.mitchellbosecke.pebble.PebbleEngine;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinPebble;

public class Project2 {
    public static void main(String[] args){
        JavalinPebble.INSTANCE.configure(new PebbleEngine.Builder().build());
        JavalinRenderer.register(JavalinPebble.INSTANCE, ".html");
        Javalin app = Javalin.create(
                config -> {
                    config.addStaticFiles("src/main/resources/public", Location.EXTERNAL);
                }
             );
        app.start(9090);
        app.get("/", ctx -> {
            ctx.redirect("login.html");
        });




>>>>>>> e67ca52 (doing dao)
    }

}

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import dao.BugCommentDao;
import entity.Bug;
import entity.BugComment;
import dao.BugDaoImpl;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinPebble;

import java.sql.Timestamp;
import java.util.Date;

public class Project2 {
    public static void main(String[] args) {
//        User user=User.builder().
//                firstname("Hiep").
//                lastname("Huynh").
//                build();
//        System.out.println(user.getFirstname());
//        BugCommentDao bc = new BugCommentDao();
//        Integer test = bc.insert(new BugComment(0, 2, 1, "This is a test", new Date(System.currentTimeMillis())));
//        System.out.println(test);

        BugDaoImpl bugDao= new BugDaoImpl();

//        System.out.println(bugDao.getById(2).getBug_id());

//        System.out.println(bugDao.update(Bug.builder()
//                        .bug_id(8)
//                        .description("testing updating bug 8 description")
//                .creator_id(1)
//                .issueDate(new Date())
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


    }

}

import controller.BugCommentController;

import io.javalin.Javalin;

public class Project2 {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(9090);

        // later will be bug/{bug_id}/comments
        app.post("/comments", BugCommentController.viewAllCommentsRequest);
        app.post("/comments/new-comment", BugCommentController.createCommentRequest);
    }
}

package controller;

import domain.repsonse.BugCommentResponse;
import entity.BugComment;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import service.BugCommentService;

import java.util.List;

public class BugCommentController {
    private static BugCommentService bugCommentService = new BugCommentService();

    private static BugComment deserializeComment(Context ctx) {
        // Convert JSON to class
        try {
            BugComment comment = ctx.bodyAsClass(BugComment.class);
            return comment;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Handler createCommentRequest = ctx -> {
        BugComment bugComment = deserializeComment(ctx);
        System.out.println(bugComment);
        BugCommentResponse creationResult = bugCommentService.createComment(bugComment);
        if (creationResult != null) {
            ctx.json(creationResult);
        }
        else {
            ctx.result("Failed to make comment. Make sure you have the proper format and an existing bug.");
            ctx.status(400);
        }
    };

    public static Handler viewAllCommentsRequest = ctx -> {
        Integer id = Integer.parseInt(ctx.pathParam("bug_id"));
        System.out.println(id);
        List<BugCommentResponse> comments = bugCommentService.getCommentsForBug(id);
        if (comments != null) {
            ctx.json(comments);
        }
        else {
            ctx.result("Bug doesn't exist");
            ctx.status(404);
        }
    };

    public static Handler getCommentByIdRequest = ctx -> {
        Integer id = Integer.parseInt(ctx.pathParam("commentId"));

        BugCommentResponse comment = bugCommentService.getCommentById(id);

        if (comment != null) {
            ctx.json(comment);
        }
        else {
            ctx.result("Comment doesn't exist");
            ctx.status(404);
        }
    };
}

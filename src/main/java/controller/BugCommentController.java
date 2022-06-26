package controller;

import domain.repsonse.BugCommentResponse;
import entity.Bug;
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
        BugCommentResponse creationResult = bugCommentService.createComment(bugComment);
        if (creationResult != null) {
            ctx.json(creationResult);
        }
        else {
            ctx.result("There was an issue");
        }
    };

    private static Bug deserializeBug(Context ctx) {
        // Convert JSON to class
        try {
            Bug bug = ctx.bodyAsClass(Bug.class);
            return bug;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Handler viewAllCommentsRequest = ctx -> {
        Bug bug = deserializeBug(ctx);
        List<BugCommentResponse> comments = bugCommentService.getCommentsForBug(bug);
        if (comments != null) {
            ctx.json(comments);
        }
        else {
            ctx.result("There's an issue");
        }
    };
}

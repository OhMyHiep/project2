package service;

import dao.BugCommentDao;
import dao.BugDaoImpl;
import entity.Bug;
import entity.BugComment;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class BugCommentService {
    private BugComment deserializeComment(Context ctx) {
        // Convert JSON to class
        BugComment comment = ctx.bodyAsClass(BugComment.class);
        return comment;
    }

    private boolean getCommentLength(BugComment bugComment) {
        // Check length to be in between 10 and 1000
        if (bugComment.getCommentText().length() > 1000 && bugComment.getCommentText().length() < 10) {
            return true;
        }
        return false;
    }

    public BugComment createComment(Context ctx) {
        BugComment bugComment = deserializeComment(ctx);
        // Checks if valid json
        if (bugComment != null) {
            boolean lengthValid = getCommentLength(bugComment);
            // Check valid length
            if (lengthValid) {
                BugCommentDao bugCommentDao = new BugCommentDao();
                bugCommentDao.insert(bugComment);
            }
        }
        return null;
    }

    private Bug deserializeBug(Context ctx) {
        // Convert JSON to class
        Bug bug = ctx.bodyAsClass(Bug.class);
        return bug;
    }

    // Send the Bug object to here, since need bug id
    public List<BugComment> getCommentsForBug(Context ctx) {
        Bug bugForList = deserializeBug(ctx);
        if (bugForList != null) {
            BugDaoImpl bugDao = new BugDaoImpl();
            Bug bugExists = bugDao.getById(bugForList.getBug_id());
            if (bugExists != null) {
                List<BugComment> listOfComments = new ArrayList<>();
                BugCommentDao bugCommentDao = new BugCommentDao();
                listOfComments = bugCommentDao.getAllByBugId(bugExists.getBug_id());
                return listOfComments;
            }
        }
        return null;

    }
}

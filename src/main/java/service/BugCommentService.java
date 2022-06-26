package service;

import dao.BugCommentDao;
import dao.BugDaoImpl;
import domain.repsonse.BugCommentResponse;
import entity.Bug;
import entity.BugComment;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class BugCommentService {

    private BugCommentResponse convertToCommentResponse(BugComment bugComment) {
        return BugCommentResponse.builder()
                .commentId(bugComment.getCommentId())
                .bugId(bugComment.getBugId())
                .commenterId(bugComment.getCommenterUserId())
                .commentText(bugComment.getCommentText())
                .commentDate(bugComment.getCommentDate())
                .build();
    }

    public boolean getCommentLength(BugComment bugComment) {
        // Check length to be in between 10 and 1000
        if (bugComment.getCommentText().length() <= 1000 && bugComment.getCommentText().length() >= 10) {
            return true;
        }
        return false;
    }

    public BugCommentResponse createComment(BugComment bugComment) {
        // Checks if valid json
        if (bugComment != null) {
            boolean lengthValid = getCommentLength(bugComment);
            // Check valid length
            if (lengthValid) {
                BugCommentDao bugCommentDao = new BugCommentDao();
                Integer result = bugCommentDao.insert(bugComment);
                if (result > 0) {
                    return BugCommentResponse.builder()
                            .commentId(result)
                            .build();
                }
            }
        }
        return null;
    }

    // Send the Bug object to here, since need bug id
    public List<BugCommentResponse> getCommentsForBug(Bug bugForList) {
        // Check valid JSON
        if (bugForList != null) {
            BugDaoImpl bugDao = new BugDaoImpl();
            Bug bugExists = bugDao.getById(bugForList.getBug_id());
            // Check if bug exists
            if (bugExists != null) {
                List<BugComment> listOfComments;
                BugCommentDao bugCommentDao = new BugCommentDao();
                // Return comments for specific bug
                listOfComments = bugCommentDao.getAllByBugId(bugExists.getBug_id());
                //Convert to response
                List<BugCommentResponse> listOfResponses = new ArrayList<>();
                for (BugComment comment : listOfComments) {
                    listOfResponses.add(convertToCommentResponse(comment));
                }
                return listOfResponses;
            }
        }
        return null;

    }
}

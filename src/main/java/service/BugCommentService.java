package service;

import dao.BugCommentDao;
import dao.BugDaoImpl;
import dao.UserDao;
import domain.repsonse.BugCommentResponse;
import entity.Bug;
import entity.BugComment;
import entity.User;
import entity.dto.BugCommentDto;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BugCommentService {

    private BugDaoImpl bugDao = new BugDaoImpl();
    private BugCommentDao bugCommentDao = new BugCommentDao();
    private UserDao userDao = new UserDao();


    public static BugCommentDto BugCommentDtoMapper(BugComment comment){
        return BugCommentDto.builder()
                .url("/placeholder/"+comment.getCommentId())
                .build();
    }

    private BugCommentResponse convertToCommentResponse(BugComment bugComment, BugCommentDto commentDto) {
        return BugCommentResponse.builder()
                .commentId(bugComment.getCommentId())
                .bugId(bugComment.getBugId())
                .commenterId(bugComment.getCommenterUserId())
                .commentText(bugComment.getCommentText())
                .commentDate(bugComment.getCommentDate())
                .commentDto(commentDto)
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
        Bug bugExists = bugDao.getById(bugComment.getBugId());
        User commenterExists = userDao.getById(bugComment.getCommenterUserId());
        //date has valid format
        if (bugComment != null && bugExists != null && commenterExists != null) {
            boolean lengthValid = getCommentLength(bugComment);
            // Check valid length
            if (lengthValid) {
                Integer result = bugCommentDao.insert(bugComment);
                if (result > 0) {
                    BugCommentDto dtoComment = BugCommentDtoMapper(bugComment);
                    return BugCommentResponse.builder()
                            .commentId(result)
                            .bugId(bugComment.getBugId())
                            .commenterId(bugComment.getCommenterUserId())
                            .commentText(bugComment.getCommentText())
                            .commentDate(bugComment.getCommentDate())
                            .commentDto(dtoComment)
                            .build();
                }
            }
        }
        return null;
    }

    // Send the Bug object to here, since need bug id
    public List<BugCommentResponse> getCommentsForBug(Integer bugId) {
        Bug bugExists = bugDao.getById(bugId);
        // Check if bug exists
        if (bugExists != null) {
            List<BugComment> listOfComments;
            // Return comments for specific bug
            listOfComments = bugCommentDao.getAllByBugId(bugExists.getBug_id());
            //Convert to response
            List<BugCommentResponse> listOfResponses = new ArrayList<>();
            for (BugComment comment : listOfComments) {
                BugCommentDto commentDto = BugCommentDtoMapper(comment);
                listOfResponses.add(convertToCommentResponse(comment, commentDto));
            }
            return listOfResponses;
        }
        return null;

    }
}

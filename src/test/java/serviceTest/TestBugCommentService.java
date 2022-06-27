package serviceTest;


import dao.BugCommentDao;
import dao.BugDaoImpl;
import dao.UserDao;
import domain.repsonse.BugCommentResponse;
import entity.Bug;
import entity.BugComment;
import entity.User;
import entity.dto.BugCommentDto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.BugCommentService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class TestBugCommentService {
    @InjectMocks
    private BugCommentService bugCommentService;

    @Mock
    private BugCommentDao mockBugCommentDao;

    @Mock
    private BugDaoImpl mockBugDao;

    @Mock
    private UserDao mockUserDao;

    private Bug bug;
    private BugComment comment;
    private BugCommentResponse commentResponse;
    private User user1;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeMethod
    public void setUpObjects() {
        user1 = User.builder().user_id(1).firstname("John").lastname("Smith").username("JohnS").passwd("12345").authToken("yo").build();
        bug = Bug.builder().bug_id(1).creator_id(1).description("Won't run with dependencies").build();

        comment = BugComment.builder().commentId(1).bugId(1).commenterUserId(1).commentText("Maybe try and reload the pom file").commentDate(null).build();

        BugCommentDto bugCommentDto = bugCommentService.BugCommentDtoMapper(comment);
        commentResponse = BugCommentResponse.builder().commentId(comment.getCommentId()).bugId(comment.getBugId()).commenterId(comment.getCommenterUserId()).commentText(comment.getCommentText()).commentDate(comment.getCommentDate()).commentUrl(bugCommentDto).build();
    }

    @Test
    public void testGetBugIdComments_Success() {
        when(mockBugCommentDao.getAllByBugId(1)).thenReturn(Arrays.asList(comment));
        when(mockBugDao.getById(1)).thenReturn(bug);

        List<BugCommentResponse> commentList = bugCommentService.getCommentsForBug(1);
        comment.setCommentDate(commentList.get(0).getCommentDate());
        Assert.assertEquals(commentList, Arrays.asList(commentResponse));
    }
    @Test
    public void testGetBugIdComments_Failure_notExisting1() {
        when(mockBugCommentDao.getAllByBugId(-1)).thenReturn(null);
        when(mockBugDao.getById(-1)).thenReturn(null);

        List<BugCommentResponse> commentList = bugCommentService.getCommentsForBug(-1);
        Assert.assertEquals(commentList, null);
    }

    @Test
    public void testGetBugIdComments_Failure_notExisting2() {
        when(mockBugCommentDao.getAllByBugId(4000)).thenReturn(null);
        when(mockBugDao.getById(4000)).thenReturn(null);

        List<BugCommentResponse> commentList = bugCommentService.getCommentsForBug(4000);
        Assert.assertEquals(commentList, null);
    }

    @Test
    public void testAddComment_Success() {
        when(mockBugCommentDao.insert(comment)).thenReturn(1);
        when(mockBugDao.getById(1)).thenReturn(bug);
        when(mockUserDao.getById(1)).thenReturn(user1);

        BugCommentResponse res = bugCommentService.createComment(comment);
        commentResponse.setCommentDate(res.getCommentDate());
        Assert.assertEquals(res, commentResponse);
    }

    @Test(dataProvider = "commentFailureProvider", dataProviderClass = BugCommentServiceDataProvider.class)
    public void testAddComment_Failure_NotExisting(BugComment bugComment, Bug resultBug, User resultUser) {
        when(mockBugCommentDao.insert(bugComment)).thenReturn(anyInt());
        when(mockBugDao.getById(bugComment.getBugId())).thenReturn(resultBug);
        when(mockUserDao.getById(bugComment.getCommenterUserId())).thenReturn(resultUser);

        BugCommentResponse res = bugCommentService.createComment(bugComment);
        Assert.assertEquals(res, null);
    }

    @Test(dataProvider = "commentLengthProvider", dataProviderClass = BugCommentServiceDataProvider.class)
    public void givenComment_WhenCheckLength_ThenAcceptOrDeny(BugComment bugComment, boolean result){
        Assert.assertEquals(bugCommentService.getCommentLength(bugComment), result);
    }

    @Test(dataProvider = "commentInvalidInputProvider", dataProviderClass = BugCommentServiceDataProvider.class)
    public void givenComment_WhenCheckValid_ThenAcceptOrDeny(BugComment bugComment, boolean result, User outputUser, Bug outputBug){
        when(mockBugDao.getById(bugComment.getBugId())).thenReturn(outputBug);
        when(mockUserDao.getById(bugComment.getCommenterUserId())).thenReturn(outputUser);
        Assert.assertEquals(bugCommentService.validateValidInputForComments(bugComment), result);
    }
}

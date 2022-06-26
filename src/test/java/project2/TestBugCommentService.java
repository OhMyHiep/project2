package project2;


import entity.BugComment;
import net.bytebuddy.utility.RandomString;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.BugCommentService;

import java.sql.Date;

import static org.mockito.Mockito.when;

public class TestBugCommentService {
    @Mock
    private BugCommentService mockBugCommentService;


    @BeforeClass
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test(dataProvider = "commentProvider")
    public void givenComment_WhenCheckLength_ThenAcceptOrDeny(BugComment bugComment, boolean result){
        when(mockBugCommentService.getCommentLength(bugComment)).thenReturn(result);

        Assert.assertEquals(mockBugCommentService.getCommentLength(bugComment), result);
    }

    @DataProvider
    public Object[][] commentProvider() {
        Object[][] data = new Object[4][2];

        // 1st row
        data[0][0] = BugComment.builder()
                .commentId(1)
                .bugId(1)
                .commenterUserId(1)
                .commentText("Hello there this is a comment")
                .commentDate(new Date(System.currentTimeMillis()))
                .build();
        data[0][1] = true;

        //2nd row
        data[1][0] = BugComment.builder()
                .commentId(70)
                .bugId(3)
                .commenterUserId(40)
                .commentText("No way")
                .commentDate(new Date(System.currentTimeMillis()))
                .build();
        data[1][1] = false;

        // 3rd row
        data[2][0] = BugComment.builder()
                .commentId(23)
                .bugId(48)
                .commenterUserId(12)
                .commentText("")
                .commentDate(new Date(System.currentTimeMillis()))
                .build();
        data[2][1] = false;

        // 4th row
        data[3][0] = BugComment.builder()
                .commentId(2)
                .bugId(98)
                .commenterUserId(34)
                .commentText(RandomString.make(1000))
                .commentDate(new Date(System.currentTimeMillis()))
                .build();
        data[3][1] = false;

        return data;
    }
}

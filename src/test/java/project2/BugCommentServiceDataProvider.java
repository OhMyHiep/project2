package project2;

import entity.Bug;
import entity.BugComment;
import entity.User;
import net.bytebuddy.utility.RandomString;
import org.testng.annotations.DataProvider;

import java.sql.Date;

public class BugCommentServiceDataProvider {

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
                .commentText(RandomString.make(1001))
                .commentDate(new Date(System.currentTimeMillis()))
                .build();
        data[3][1] = false;

        return data;
    }

    @DataProvider
    public Object[][] commentFailureProvider() {
        User user1 = User.builder().user_id(1).firstname("John").lastname("Smith").username("JohnS").passwd("12345").authToken("yo").build();
        Bug bug = Bug.builder().bug_id(1).creator_id(1).description("Won't run with dependencies").build();

        Object[][] data = new Object[4][3];

        data[0][0] = BugComment.builder().commentId(1).bugId(-1).commenterUserId(1).commentText("Maybe try and reload the pom file").commentDate(new Date(System.currentTimeMillis())).build();
        data[0][1] = null;
        data[0][2] = user1;

        data[1][0] = BugComment.builder().commentId(1).bugId(1).commenterUserId(1).commentText("").commentDate(new Date(System.currentTimeMillis())).build();
        data[1][1] = bug;
        data[1][2] = user1;

        data[2][0] = BugComment.builder().commentId(1).bugId(1).commenterUserId(1).commentText(RandomString.make(1001)).commentDate(new Date(System.currentTimeMillis())).build();
        data[2][1] = bug;
        data[2][2] = user1;

        data[3][0] = BugComment.builder().commentId(1).bugId(1).commenterUserId(34).commentText("This is a comment, hello.").commentDate(new Date(System.currentTimeMillis())).build();
        data[3][1] = bug;
        data[3][2] = null;

        return data;
    }
}

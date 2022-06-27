package daoTest;

import entity.BugComment;
import net.bytebuddy.utility.RandomString;
import org.testng.annotations.DataProvider;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

public class BugCommentDaoDataProvider {
    @DataProvider
    public Object[][] commentIdProvider() {
        Object[][] data = new Object[3][2];

        Long datenum = new Long("1655870400000");

        data[0][0] = new Integer(1);
        data[0][1] = BugComment.builder().commentId(1).bugId(2).commenterUserId(1).commentText("Yo").commentDate(new Date(datenum)).build();

        data[1][0] = new Integer(-20);
        data[1][1] = null;

        data[2][0] = new Integer(4736843);
        data[2][1] = null;

        return data;
    }

    @DataProvider
    public Object[][] bugIdProvider() {
        Object[][] data = new Object[3][2];

        Long datenum = new Long("1656302400000");

        data[0][0] = new Integer(9);
        data[0][1] = Arrays.asList(BugComment.builder().commentId(7).bugId(9).commenterUserId(1).commentText("This is a comment").commentDate(new Date(datenum)).build());

        data[1][0] = new Integer(-20);
        data[1][1] = new ArrayList<BugComment>();

        data[2][0] = new Integer(4736843);
        data[2][1] = new ArrayList<BugComment>();

        return data;
    }

    @DataProvider
    public Object[][] commentForDaoProvider() {
        Object[][] data = new Object[3][2];

        data[0][0] = BugComment.builder().bugId(-12).commenterUserId(1).commentText("This is a comment for tests").commentDate(new Date(System.currentTimeMillis())).build();
        data[0][1] = -1;

        data[1][0] = BugComment.builder().bugId(1).commenterUserId(30).commentText("This is a comment for tests").commentDate(new Date(System.currentTimeMillis())).build();
        data[1][1] = -1;

        data[2][0] = BugComment.builder().bugId(-12).commenterUserId(1).commentText(RandomString.make(1001)).commentDate(new Date(System.currentTimeMillis())).build();
        data[2][1] = -1;

        return data;
    }
}

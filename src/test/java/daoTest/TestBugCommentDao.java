package daoTest;

import dao.BugCommentDao;
import entity.BugComment;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Date;
import java.util.List;

public class TestBugCommentDao {

    private BugCommentDao bugCommentDao;
    private BugComment insertComment;

    @BeforeClass
    public void setUp() {
        bugCommentDao = new BugCommentDao();
        insertComment = BugComment.builder().bugId(2).commenterUserId(1).commentText("This is a comment for tests").commentDate(new Date(System.currentTimeMillis())).build();

    }

    @Test(dataProvider = "commentIdProvider", dataProviderClass = BugCommentDaoDataProvider.class)
    public void testGetById(Integer commentId, BugComment bugCommentOutput) {
        BugComment result = bugCommentDao.getById(commentId);

        Assert.assertEquals(result, bugCommentOutput);
    }

    @Test(dataProvider = "bugIdProvider", dataProviderClass = BugCommentDaoDataProvider.class)
    public void testGetAllByBugId(Integer bugId, List<BugComment> listOfComments) {
        List<BugComment> listResults = bugCommentDao.getAllByBugId(bugId);

        Assert.assertEquals(listResults, listOfComments);
    }

    @Test(dataProvider = "commentForDaoProvider", dataProviderClass = BugCommentDaoDataProvider.class)
    public void testInsert_failure(BugComment inputComment, Integer expectedNum) {
        Integer result = bugCommentDao.insert(inputComment);

        Assert.assertEquals(result, expectedNum);
    }

    @Test
    public void testInsert_success() {
        Integer result = bugCommentDao.insert(insertComment);

        Assert.assertTrue(result > 0);
    }
}
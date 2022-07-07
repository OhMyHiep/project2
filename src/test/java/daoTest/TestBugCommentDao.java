package daoTest;

import dao.BugCommentDao;
import entity.BugComment;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.jws.Oneway;
import java.sql.Date;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

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

    @Test
    public void testGetAll(){
        assertTrue(bugCommentDao.getAll().size()>0);
    }

    @Test
    public void testDeleteById(){
        assertTrue(bugCommentDao.deleteById(insertComment.getBugId())!=null);
    }

    public void testGetBugByCreatorId() {
        List<BugComment> comments = bugCommentDao.getBugByCreatorId(1);

        Assert.assertNotNull(comments);
    }

    @Test (dataProvider = "dataForCreatorId")
    public void testGetBugByCreatorIdFail(Integer id) {
        List<BugComment> comments = bugCommentDao.getBugByCreatorId(id);

        Assert.assertTrue(comments.size() < 1);
    }

    @DataProvider
    public Object[][] dataForCreatorId() {
        return new Object[][] {
                {3000}, {500}, {422}
        };
    }
}
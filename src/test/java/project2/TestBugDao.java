package project2;

import dao.BugDaoImpl;
import entity.Bug;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Date;

public class TestBugDao {

    BugDaoImpl bugDao = new BugDaoImpl();
    Bug validBug;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validBug=Bug.builder()
                .bug_id(1000)
                .issueDate(new Date(System.currentTimeMillis()-1))
                .assigned_to(1)
                .creator_id(1)
                .description("this is more than fifty characters for the description")
                .status(1)
                .urgency(1)
                .severity(1)
                .build();
        bugDao.insert(validBug);

    }


    @DataProvider
    public Object[][] bugIdProvider(){
        return new Object[][]{
                {null,null},{1000,validBug},{1001,null}
        };
    }
    @Test(dataProvider = "bugIdProvider")
    public void test_getById(Integer input,Bug expected){
        Bug result=bugDao.getById(input);
        Assert.assertTrue(result==null? expected==null: result.equals(expected));
    }

    @AfterClass
    public void tearDown(){
        bugDao.deleteById(1000);
    }




}

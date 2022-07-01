package daoTest;

import dao.BugDaoImpl;
import entity.Bug;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

import static org.testng.Assert.*;

public class TestBugDao {

    BugDaoImpl bugDao = new BugDaoImpl();
    Bug validBug;
    Bug validInsertBugNoId;
    Bug invalidBug;
    Bug validBug2;
    ArrayList<Integer> idToRemoveFromDb = new ArrayList<>();
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

        validBug2=Bug.builder()
                .bug_id(1200)
                .issueDate(new Date(System.currentTimeMillis()-1))
                .assigned_to(1)
                .creator_id(1)
                .description("this is more than fifty characters for the description")
                .status(1)
                .urgency(1)
                .severity(1)
                .build();

        validInsertBugNoId=Bug.builder()
                .issueDate(new Date(System.currentTimeMillis()-1))
                .assigned_to(1)
                .creator_id(1)
                .description("this is more than fifty characters for the description")
                .status(1)
                .urgency(1)
                .severity(1)
                .build();

        invalidBug=Bug.builder().build();
        bugDao.insert(validBug);
        idToRemoveFromDb.add(1000);
    }

    @DataProvider
    public Object[][] bugProvider(){
        return new Object[][]{
                {validBug2},{validInsertBugNoId}
        };
    }
    @Test(dataProvider = "bugProvider")
    public void test_insert(Bug input){
        Integer id= bugDao.insert(input);
        input.setBug_id(id);
        assertEquals(input,bugDao.getById(id));
        idToRemoveFromDb.add(id);
        validInsertBugNoId.setBug_id(null);
    }

    @Test(expectedExceptions ={NullPointerException.class})
    public void test_insert_incorrectInputs(){
        bugDao.insert(invalidBug);
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
        assertTrue(Objects.equals(result, expected));
    }
    @Test(dataProvider = "bugIdProvider")
    public void test_deleteById(Integer input,Bug expected){
        Bug result=bugDao.getById(input);
        assertTrue(result==null? expected==null: result.getBug_id().equals(expected.getBug_id()));
    }


    @DataProvider
    public Object[][] bugUpdateProvider(){
        return new Object[][]{
                {1000,null},{null,NullPointerException.class},{1001,NullPointerException.class}
        };

    }
    @Test(dataProvider = "bugUpdateProvider")
    public void test_update(Integer input,Class<? extends Exception> exception){
        try{
            Bug fromBug= Bug.builder()
                    .bug_id(input)
                    .issueDate(new Date(System.currentTimeMillis()-100))
                    .creator_id(1)
                    .status(9)
                    .assigned_to(1)
                    .severity(9)
                    .urgency(9)
                    .build();
            Integer updatedBugId=bugDao.update(fromBug);
            assertTrue(fromBug.equals(bugDao.getById(updatedBugId)));
        }
        catch(Exception e){
            assertEquals(e.getClass(),exception);
        }

    }

    @Test
    public void test_getAll(){
        assertTrue(bugDao.getAll().size()>0);
    }


    @AfterClass
    public void tearDown(){
        for(Integer i:idToRemoveFromDb) bugDao.deleteById(i);
    }




}

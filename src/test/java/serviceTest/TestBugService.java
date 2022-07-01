package serviceTest;

import dao.BugCommentDao;
import dao.BugDaoImpl;
import dao.UserDao;
import domain.repsonse.BugListResponse;
import domain.repsonse.BugResponse;
import entity.Bug;
import entity.User;
import entity.dto.BugDto;
import entity.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.BugService;
import service.UserService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;

public class TestBugService {
    @InjectMocks
    private BugService mockBugService;

    @Mock
    private UserDao mockUserDao;
    @Mock
    private BugDaoImpl mockBugDao;
    @Mock
    private BugCommentDao mockCommentDao;

    private Bug validBug;
    private Bug invalidBug=null;
    private Bug invalidBugNumber2;
    private User validUser;
    private User invalidUser=null;
    private BugResponse validBugResponse;
    private BugDto validBugDto;
    private UserDto validUserDto;

    BugListResponse validBugListResponse;

    @BeforeClass
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        validBug=Bug.builder()
                .bug_id(1)
                .description("this is more than fifty characters for the description")
                .urgency(1)
                .severity(1)
                .status(1)
                .creator_id(1)
                .closeDate(new Date(System.currentTimeMillis()+1))
                .issueDate(new Date(System.currentTimeMillis()-1))
                .assignDate(new Date(System.currentTimeMillis()))
                .assigned_to(1)
                .build();
        invalidBugNumber2= Bug.builder().bug_id(90000).build();
        validUser=User.builder().user_id(1).build();
        validBugDto=BugDto.builder().url("/bug/"+validBug.getBug_id()).build();
        validBugListResponse= BugListResponse.builder()
                .bugs(
                        new ArrayList<>(Arrays.asList(validBugDto)))
                .build();

        validUserDto= UserService.userDtoMapper(validUser);

        validBugResponse=BugResponse.builder()
                .bug_id(validBug.getBug_id())
                .assignDate(validBug.getAssignDate())
                .closeDate(validBug.getCloseDate())
                .description(validBug.getDescription())
                .issueDate(validBug.getIssueDate())
                .severity(validBug.getSeverity())
                .status(validBug.getStatus())
                .urgency(validBug.getUrgency())
                .comment(new ArrayList<>())
                .creator(validUserDto)
                .assigned_to(validUserDto)
                .self(validBugDto)
                .build();

        when(mockBugDao.getById(1)).thenReturn(validBug);
        when(mockUserDao.getById(1)).thenReturn(validUser);

        when(mockBugDao.getById(90000)).thenReturn(invalidBug);
        when(mockUserDao.getById(90000)).thenReturn(invalidUser);

        when(mockBugDao.deleteById(1)).thenReturn(validBug.getBug_id());
        when(mockBugDao.deleteById(90000)).thenReturn(null);
        when(mockBugDao.update(validBug)).thenReturn(validBug.getBug_id());
        when(mockBugDao.update(invalidBugNumber2)).thenReturn(null);
        when(mockBugDao.insert(validBug)).thenReturn(validBug.getBug_id());
        when(mockBugDao.insert(invalidBugNumber2)).thenReturn(null);
        when(mockBugDao.getAll()).thenReturn(new ArrayList<>(Arrays.asList(validBug)));
        when(mockCommentDao.getAllByBugId(validBug.getBug_id())).thenReturn(new ArrayList<>());

    }

    @DataProvider
    public Object[][] urgencySeverityProviderForUpdate(){
        return new Object[][] {
                {null, true}, {0,false},{1,true},{9,true},{10,false}
        };
    }
    @Test(dataProvider="urgencySeverityProviderForUpdate")
    public void test_severityIsValidForUpdate(Integer input, boolean expected){
        Assert.assertEquals(mockBugService.severityIsValidForUpdate(input),expected);
    }
    @Test(dataProvider="urgencySeverityProviderForUpdate")
    public void test_urgencyIsValidForUpdate(Integer input, boolean expected){
        Assert.assertEquals(mockBugService.urgencyIsValidForUpdate(input),expected);
    }


    @DataProvider
    public Object[][] descriptionProviderForUpdate(){
        return new Object[][]{
                {null,true},{"this is more than fifty characters for the description",true},
                {"           this is more than fifty characters with white spaces    ",true},
                {StringUtils.repeat("*", 1000),true},
                {StringUtils.repeat("*", 1001),false},
                {"less than 50 characters",false}
        };

    }
    @Test(dataProvider="descriptionProviderForUpdate")
    public void test_descriptionProviderForUpdate(String input, boolean expected){
        Assert.assertEquals(mockBugService.descriptionIsValidForUpdate(input),expected);
    }

    @DataProvider
    public Object[][] creatorProviderForInsert() {
        return new Object[][]{
                {null, false}, {1, true},{90000,false}
        };
    }
    @Test(dataProvider="creatorProviderForInsert")
    public void test_creator_idIsValidForInsert(Integer input, boolean expected){
        Assert.assertEquals(mockBugService.creator_idIsValidForInsert(input),expected);
    }

    @DataProvider
    public Object[][] statusProviderForUpdate(){
        return new Object[][]{
                {null, true}, {1, true},{0,false},{4,true},{5,false}
        };
    }
    @Test(dataProvider="statusProviderForUpdate")
    public void test_statusIsValidForUpdate(Integer input, boolean expected){
        Assert.assertEquals(mockBugService.statusIsValidForUpdate(input),expected);
    }


    @DataProvider
    public Object[][] descriptionProviderForInsert(){
        return new Object[][]{
                {null,false},{"this is more than fifty characters for the description",true},
                {"           this is more than fifty characters with white spaces    ",true},
                {StringUtils.repeat("*", 1000),true},
                {StringUtils.repeat("*", 1001),false},
                {"less than 50 characters",false}
        };
    }
    @Test(dataProvider = "descriptionProviderForInsert")
    public void test_descriptionIsValidForInsert(String input,boolean expected){
        Assert.assertEquals(mockBugService.descriptionIsValidForInsert(input),expected);
    }


    @DataProvider
    public Object[][] urgencySeverityProviderForInsert(){
        return new Object[][] {
                {null, false}, {0,false},{1,true},{9,true},{10,false}
        };
    }
    @Test(dataProvider="urgencySeverityProviderForInsert")
    public void test_urgencyIsValidForInsert(Integer input,boolean expected){
        Assert.assertEquals(mockBugService.urgencyIsValidForInsert(input),expected);
    }
    @Test(dataProvider="urgencySeverityProviderForInsert")
    public void test_severityIsValidForInsert(Integer input,boolean expected){
        Assert.assertEquals(mockBugService.severityIsValidForInsert(input),expected);
    }


    @DataProvider
    public Object[][] IdProviderForUpdate(){
        return new Object[][]{
                {1,true},{null,false}
        };
    }
    @Test(dataProvider="IdProviderForUpdate")
    public void test_idIsValidForUpdate(Integer input,boolean expected){
        Assert.assertEquals(mockBugService.idIsValidForUpdate(input),expected);
    }

    @DataProvider
    public Object[][] assigned_toProviderForUpdate(){
        return new Object[][]{
                {null,false} ,{1,true},{90000,false}
        };
    }
    @Test(dataProvider="assigned_toProviderForUpdate")
    public void test_assigned_toIsValidForUpdate(Integer input,boolean expected){
        Assert.assertEquals(mockBugService.assigned_toIsValidForUpdate(input),expected);
    }


    @Test
    public void test_transferAttributes(){
        Bug tempBug= new Bug();
        mockBugService.transferAttributes(tempBug,validBug);
        Assert.assertEquals(tempBug,validBug);
    }

    @Test
    public void test_transferAttributes_nullCheck(){
        Bug tempBug= Bug.builder().bug_id(3).build();
        mockBugService.transferAttributes(tempBug,validBug);
        Assert.assertNotEquals(tempBug,validBug);
    }


    @DataProvider
    public Object[][] idProviderTemplate(){
        return new Object[][]{
                {null,null} ,{1,validBugListResponse},{90000,null}
        };
    }
    @Test(dataProvider = "idProviderTemplate")
    public void test_deleteById(Integer id,BugListResponse expected){
        Assert.assertEquals(mockBugService.deleteById(id),expected);
    }

    @Test
    public void test_update_sucess(){
        Bug tempBug= Bug.builder().bug_id(1).build();
        Assert.assertEquals(mockBugService.update(tempBug),validBugListResponse);
    }
    @Test
    public void test_update_incorrectID(){
        Bug tempBug= Bug.builder().bug_id(90000).build();
        Assert.assertEquals(mockBugService.update(tempBug),null);
    }

    @DataProvider
    public Object[][] bugListResponseProvider(){
        return new Object[][]{
                {null,null},{validBug,validBugListResponse},{invalidBugNumber2,null}
        };
    }
    @Test(dataProvider = "bugListResponseProvider")
    public void test_insert(Bug input, BugListResponse expected){
        Assert.assertEquals(mockBugService.insert(input),expected);
    }

    @Test
    public void test_getAll(){
        Assert.assertEquals(mockBugService.getAll(),validBugListResponse);
    }

    @DataProvider
    public Object[][] bugResponseProvider(){
        return new Object[][]{
                {null,null},{1,validBugResponse},{90000,null}
        };
    }
    @Test(dataProvider = "bugResponseProvider")
    public void test_getById(Integer input,BugResponse expected){
        Assert.assertEquals(mockBugService.getById(input),expected);
    }

    @Test
    public void test_bugResponseMapper(){
        Assert.assertEquals(mockBugService
                .bugResponseMapper(
                        validBug,
                        validBugDto,
                        new ArrayList<>(),
                        validUserDto,
                        validUserDto),
                validBugResponse);
    }

    @Test
    public void test_bugDtoMapper(){
        Assert.assertEquals(mockBugService.bugDtoMapper(validBug),validBugDto);
    }


}

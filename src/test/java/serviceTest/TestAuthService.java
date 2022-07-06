package serviceTest;

import dao.AuthenticationDao;
import dao.RoleDao;
import dao.UserDao;
import domain.repsonse.AuthResponse;
import domain.repsonse.UserListResponse;
import domain.repsonse.UserResponse;
import domain.request.AuthRequest;
import entity.Bug;
import entity.BugComment;
import entity.Role;
import entity.User;
import entity.dto.BugCommentDto;
import entity.dto.BugDto;
import entity.dto.RoleDto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class TestAuthService {

    @Mock
    private UserDao mockUserDao;

    @Mock
    private RoleDao mockRoleDao;

    @Mock
    private AuthenticationDao mockAuthDao;

    @Mock
    private UserService mockUserService;

    @InjectMocks
    private AuthService mockAuthService;

    private User invalidUser;
    private User validUser;
    private Role invalidRole;
    private Role validRole;
    private AuthRequest invalidAuthRequest;
    private AuthRequest validAuthRequest;

    private AuthResponse validAuthResponse;
    private UserResponse validUserResponse;

    private BugDto bugDto;
    private BugCommentDto bugCommentDto;
    private RoleDto roleDto;

    private Bug bug;
    private BugComment bugComment;
    private UserListResponse userListResponse;




    @BeforeClass
    public void setup() {
        MockitoAnnotations.openMocks(this);
        invalidUser = User.builder()
                .user_id(-1)
                .build();
        invalidRole = new Role(-1,"");
        validUser = User.builder()
                .user_id(1)
                .username("email@email.com")
                .passwd("pass")
                .firstname("John")
                .lastname("Smith")
                .authToken("0")
                .build();
        validRole = new Role(1,"programmer");
        invalidAuthRequest = AuthRequest.builder()
                .username("")
                .build();
        validAuthRequest = AuthRequest.builder()
                .username("email@email.com")
                .password("pass")
                .build();

        bug = Bug.builder()
                .bug_id(1)
                .creator_id(1)
                .description("bro")
                .assigned_to(2)
                .issueDate(new Date(10000))
                .urgency(1)
                .severity(1)
                .closeDate(new Date(10001))
                .title("haha")
                .status(1)
                .build();

        bugComment = BugComment.builder()
                .bugId(1)
                .commentDate(new Date(10000))
                .commentText("bro man")
                .commenterUserId(2)
                .commentId(1)
                .build();

        bugDto = BugService.bugDtoMapper(bug);
        bugCommentDto = BugCommentService.BugCommentDtoMapper(bugComment);
        ArrayList<BugDto> bugDtoArrayList = new ArrayList<>();
        bugDtoArrayList.add(bugDto);

        ArrayList<BugCommentDto> bugCommentDtoArrayList = new ArrayList<>();
        bugCommentDtoArrayList.add(bugCommentDto);

        roleDto = RoleService.roleDtoMapper(validRole);
        List<RoleDto> validRoleDtoList= new ArrayList<>(Arrays.asList(roleDto));

        validUserResponse = UserResponse.builder()
                .user_id(1)
                .firstname("John")
                .lastname("Smith")
                .assignedBugs(bugDtoArrayList)
                .createdBugs(bugDtoArrayList)
                .roles(validRoleDtoList)
                .comments(bugCommentDtoArrayList)
                .build();

        validAuthResponse = AuthResponse.builder()
                        .token("eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyX2lkIjoxLCJyb2xlcyI6IlByb2dyYW1tZXIsUHJvZ3JhbW1lciwiLCJzdWIiOiJKV1QgQXV0aCIsImp0aSI6ImNiOTg5ZGVjLWY2ZjUtNDRkYi05N2NhLThlMTlkMjZmYjM2MSIsImlhdCI6MTY1NzA3ODMyNSwiZXhwIjoxNjU3MDgxMzI1fQ.tuTsNxDFICLuXibxXAM92NZ9GcxhXTiSSksb2Yre0O3FNbi6P9hltHwIub2Fsk9kVoYko9QvsQ6Vu-zJw_B93A")
                                .user(validUserResponse)
                                        .build();

        when(mockUserDao.getById(1)).thenReturn(validUser);
        when(mockUserDao.getByCredentials("email@email.com","pass")).
                thenReturn(validUser);
        when(mockRoleDao.getById(1)).thenReturn(validRole);

        when(mockRoleDao.getByTitle("Programmer")).thenReturn(validRole);
        when(mockRoleDao.getByTitle("TechLead")).thenReturn(validRole);
        when(mockRoleDao.getByTitle("Everyone")).thenReturn(validRole);


        when(mockUserDao.getById(9999)).thenReturn(invalidUser);
        when(mockRoleDao.getById(9999)).thenReturn(invalidRole);

        when(mockAuthDao.authenticate("email@email.com","pass")).
                thenReturn("Programmer,TechLead");
        when(mockAuthDao.authenticate("lol","ha")).
                thenReturn("");
        when(mockUserService.getUserResponse(validUser)).thenReturn(validUserResponse);

    }

    @DataProvider
    public Object[][] buildJwtDataProvider() {
        return new Object[][] {
                {invalidAuthRequest, null},
                {validAuthRequest, validAuthResponse}
        };
    }

    @Test(dataProvider = "buildJwtDataProvider")
    public void test_buildJwt(AuthRequest in, AuthResponse expected) {
        AuthResponse response=mockAuthService.buildJwt(in);

        if (response != null) {
            String[] tokenParts = response.getToken().split("\\.");
            String[] tokenParts2 = expected.getToken().split("\\.");

            Assert.assertEquals(tokenParts[0],tokenParts2[0]);
            Assert.assertEquals(tokenParts[1],tokenParts2[1]);
            Assert.assertEquals(response.getUser(),expected.getUser());
        } else {
            Assert.assertEquals(response,expected);
        }
    }



}

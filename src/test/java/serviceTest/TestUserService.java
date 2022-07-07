package serviceTest;

import dao.BugCommentDao;
import dao.BugDaoImpl;
import dao.UserDao;
import domain.repsonse.UserListResponse;
import domain.repsonse.UserResponse;
import entity.Bug;
import entity.BugComment;
import entity.User;
import entity.dto.BugCommentDto;
import entity.dto.BugDto;
import entity.dto.UserDto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.BugCommentService;
import service.BugService;
import service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;


public class TestUserService {

    @InjectMocks
    private UserService userService;

    @Mock
    private BugDaoImpl mockBugDao;

    @Mock
    private BugCommentDao mockBugCommentDao;

    @Mock
    private UserDao mockUserDao;

    private BugCommentDto commentDto;

    private Bug validbug;

    private User validUser;
    private UserResponse validUserResponse;

    private   UserListResponse userListResponse;

    @BeforeClass
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        validUser=User.builder().user_id(1).build();
        ArrayList<User> userList=new ArrayList<>();
        userList.add(validUser);

        List<UserDto> userDtoList= userList.stream()
                .map(user -> UserService.userDtoMapper(user))
                .collect(Collectors.toList());

        userListResponse=UserListResponse.builder().users(userDtoList).build();

        ArrayList<Bug> bugs=new ArrayList<>();
        validbug=Bug.builder().title("valid title for bug").build();
        bugs.add(validbug);

        ArrayList<BugDto> bugDtos=new ArrayList<>();
        bugDtos.add(BugService.bugDtoMapper(validbug));

        ArrayList<BugComment> bugComments=new ArrayList<>();
        BugComment bugComment= BugComment.builder().commentId(1).build();
        bugComments.add(bugComment);

        commentDto=BugCommentService.BugCommentDtoMapper(bugComment);
        ArrayList<BugCommentDto> commentDtos=new ArrayList<>();
        commentDtos.add(commentDto);

        validUserResponse=UserResponse.builder()
                .user_id(1)
                .assignedBugs(bugDtos)
                .createdBugs(bugDtos)
                .comments(commentDtos)
                .build();

        when(mockBugCommentDao.getBugByCreatorId(1)).thenReturn(bugComments);
        when(mockBugDao.getBugByAssignee(1)).thenReturn(bugs);
        when(mockBugDao.getBugByCreatorId(1)).thenReturn(bugs);
        when(mockUserDao.getAll()).thenReturn(userList);
    }

    @Test
    public void test_getUserResponse(){
        assertEquals(userService.getUserResponse(validUser),validUserResponse);
    }

    @Test
    public void test_getAll(){
        assertEquals(userService.getAll(),userListResponse);
    }


}

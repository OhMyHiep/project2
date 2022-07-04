package service;

import dao.BugCommentDao;
import dao.BugDaoImpl;
import dao.UserDao;
import domain.repsonse.UserListResponse;
import domain.repsonse.UserResponse;
import entity.Bug;
import entity.BugComment;
import entity.Role;
import entity.User;
import entity.dto.BugCommentDto;
import entity.dto.BugDto;
import entity.dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private UserDao userDao=new UserDao();
    private BugDaoImpl bugDao=new BugDaoImpl();
    private BugCommentDao bugCmtDao = new BugCommentDao();


    public static UserDto userDtoMapper(User user){
        return UserDto.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .url("/user/"+user.getUser_id())
                .build();
    }

    public UserListResponse getAll(){
        List<User> userList=userDao.getAll();
        List<UserDto>userDtoList= userList.stream()
                .map(user -> userDtoMapper(user))
                .collect(Collectors.toList());
        return UserListResponse.builder().users(userDtoList).build();
    }


    public UserResponse getUserResponse(User user){
        List<Bug> created= bugDao.getBugByCreatorId(user.getUser_id());
        List<Bug> assignedBugs = bugDao.getBugByAssignee(user.getUser_id());
        List<BugComment> comments = bugCmtDao.getBugByCreatorId(user.getUser_id());
        UserResponse ur=new UserResponse();
        ur.setUser_id(user.getUser_id());
        ur.setFirstname(user.getFirstname());
        ur.setLastname(user.getLastname());
        ArrayList<BugDto> createdBugDto=new ArrayList<>();
        for(Bug b:created){
            createdBugDto.add(BugService.bugDtoMapper(b));
        }
        ur.setCreatedBugs(createdBugDto);
        ArrayList<BugDto> assignedBugsDto = new ArrayList<>();
        for(Bug b:assignedBugs){
            assignedBugsDto.add(BugService.bugDtoMapper(b));
        }
        ur.setAssignedBugs(assignedBugsDto);

        ArrayList<BugCommentDto> bugCmtDtos = new ArrayList<>();
        for (BugComment bc : comments) {
            bugCmtDtos.add(BugCommentService.BugCommentDtoMapper(bc));
        }
        ur.setComments(bugCmtDtos);

        return ur;
    }

}

package service;


import dao.BugDaoImpl;
import dao.UserDao;
import domain.repsonse.BugListResponse;
import domain.repsonse.BugResponse;
import entity.Bug;
import entity.BugComment;
import entity.User;
import entity.dto.BugDto;
import entity.dto.BugCommentDto;
import entity.dto.UserDto;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BugService {

    private BugDaoImpl bugDao = new BugDaoImpl();
    private UserDao userDao =new UserDao();

    public static BugResponse bugResponseMapper(Bug bug,BugDto bugDto, List<BugCommentDto> bugCommentDto, UserDto creator,UserDto assigned_to){
        return BugResponse.builder()
                .bug_id(bug.getBug_id())
                .assignDate(bug.getAssignDate())
                .closeDate(bug.getCloseDate())
                .description(bug.getDescription())
                .issueDate(bug.getIssueDate())
                .severity(bug.getSeverity())
                .status(bug.getStatus())
                .urgency(bug.getUrgency())
                .comment(bugCommentDto)
                .creator(creator)
                .assigned_to(assigned_to)
                .self(bugDto)
                .build();
    }

    public static BugDto bugDtoMapper(Bug bug){
        return BugDto.builder()
                .url("/bug/"+bug.getBug_id())
                .build();
    }

    public static BugCommentDto BugCommentDtoMapper(BugComment comment){
        return BugCommentDto.builder()
                .url("/placeholder/"+comment.getCommentId())
                .build();
    }

    public static UserDto userDtoMapper(User user){
        return UserDto.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .url("/placeholder/"+user.getUser_id())
                .build();
    }

    public BugResponse getById(Integer bugId) {
        if (bugId==null) return null;
        Bug bug =bugDao.getById(bugId);
        if (bug==null) return null;
        BugDto bugDto= bugDtoMapper(bug);
        List<BugCommentDto> commentDtos= bugDao.getCommentsByBugId(bugId).stream()
                .map(bugComment ->BugCommentDtoMapper(bugComment))
                .collect(Collectors.toList());
        UserDto creator= userDtoMapper(userDao.getById(bug.getCreator_id()));
        UserDto assigned_to= userDtoMapper(userDao.getById(bug.getAssigned_to()));
        return bugResponseMapper(bug,bugDto,commentDtos,creator,assigned_to);
    }

    public BugListResponse getAll() {

        return BugListResponse.builder()
                .bugs(
                        bugDao.getAll().stream()
                                .map(bug -> bugDtoMapper(bug))
                                .collect(Collectors.toList())
                )
                .build();
    }

    public BugListResponse insert(Bug bug) {
        Bug insertedBug = Bug.builder()
                .bug_id(bugDao.insert(bug))
                .build();

        return insertedBug==null? null : BugListResponse.builder()
                .bugs(
                        new ArrayList<BugDto>(Arrays.asList(bugDtoMapper(insertedBug)))
                )
                .build();
    }

    public BugListResponse update(Bug bug){
        if (bug.getBug_id()==null) return null;
        Bug queryBug=bugDao.getById(bug.getBug_id());
        if (queryBug==null) return null;
        transferAttributes(bug,queryBug);
        if(bug.getAssigned_to()==null || bug.getAssigned_to()==0) bug.setAssigned_to(null);

        BugDto updatedBugDto= bugDtoMapper(
                Bug.builder()
                        .bug_id(bugDao.update(bug))
                        .build()
        );

        return BugListResponse.builder()
                .bugs(
                        new ArrayList<BugDto>(Arrays.asList(updatedBugDto)))
                .build();
    }

    public BugListResponse deleteById(Integer id) {
        if(id==null) return null;
        Bug deletedBug= Bug.builder()
                .bug_id(bugDao.deleteById(id))
                .build();
        if (deletedBug==null) return null;

         return BugListResponse.builder()
                .bugs(
                        new ArrayList<>(Arrays.asList(bugDtoMapper(deletedBug))))
                .build();
    }


    public void transferAttributes(Bug to, Bug from){
        if(!to.getClass().isAssignableFrom(from.getClass())){
            return;
        }
        Method[] methods = to.getClass().getMethods();

        for(Method currentMethod: methods){
            if(currentMethod.getDeclaringClass().equals(to.getClass())
                    && currentMethod.getName().startsWith("get")){

                String getterMethodName = currentMethod.getName();
                String setterMethodName = getterMethodName.replace("get", "set");

                try {
                    Method setter = to.getClass().getMethod(setterMethodName, currentMethod.getReturnType());
                    Object toValue = currentMethod.invoke(to, (Object[])null);
                    Object fromValue = currentMethod.invoke(from,(Object[])null);
                    if(toValue == null){
                        setter.invoke(to, fromValue);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

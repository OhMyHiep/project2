package service;


import dao.BugCommentDao;
import dao.BugDaoImpl;
import dao.UserDao;
import domain.repsonse.BugListResponse;
import domain.repsonse.BugResponse;
import entity.Bug;
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
    private BugCommentDao bugCommentDao = new BugCommentDao();

    public static BugResponse bugResponseMapper(Bug bug,BugDto bugDto, List<BugCommentDto> bugCommentDto, UserDto creator,UserDto assigned_to){
        return BugResponse.builder()
                .bug_id(bug.getBug_id())
                .title(bug.getTitle())
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
                .build();
    }

    public static BugDto bugDtoMapper(Bug bug){
        return BugDto.builder()
                .url("/bug/"+bug.getBug_id())
                .status(bug.getStatus())
                .issueDate(bug.getIssueDate())
                .severity(bug.getSeverity())
                .closeDate(bug.getCloseDate())
                .assignDate(bug.getAssignDate())
                .title(bug.getTitle())
                .urgency(bug.getUrgency())
                .build();
    }

    public BugResponse getById(Integer bugId) {
        if (bugId==null) return null;
        Bug bug =bugDao.getById(bugId);
        if (bug==null) return null;
        BugDto bugDto= bugDtoMapper(bug);
        List<BugCommentDto> commentDtos= bugCommentDao.getAllByBugId(bugId).stream()
                .map(bugComment ->BugCommentService.BugCommentDtoMapper(bugComment))
                .collect(Collectors.toList());
        UserDto creator= UserService.userDtoMapper(userDao.getById(bug.getCreator_id()));

//        UserDto assigned_to= UserService.userDtoMapper(userDao.getById(bug.getAssigned_to()));

        User assignee= userDao.getById(bug.getAssigned_to());
        UserDto assigned_to= assignee==null?null: UserService.userDtoMapper(assignee);
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
        if(bug==null
                ||!descriptionIsValidForInsert(bug.getDescription())
                ||!severityIsValidForInsert(bug.getSeverity())
                ||!urgencyIsValidForInsert(bug.getUrgency())
                ||!creator_idIsValidForInsert(bug.getCreator_id())
                ||!titleIsValidForInsert(bug.getTitle())
        ) return null;
        bug.setStatus(1);
        bug.setIssueDate(new java.sql.Date(System.currentTimeMillis()));
        bug.setDescription(bug.getDescription().trim());
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
        if (!idIsValidForUpdate(bug.getBug_id())
                || !statusIsValidForUpdate(bug.getStatus())
                || !severityIsValidForUpdate(bug.getSeverity())
                || !urgencyIsValidForUpdate(bug.getUrgency())
                || !descriptionIsValidForUpdate(bug.getDescription())
                || !titleIsValidForUpdate(bug.getTitle())
        )return null;
        if(bug.getStatus()==4) bug.setCloseDate(new java.sql.Date(System.currentTimeMillis()));
        Bug queryBug=bugDao.getById(bug.getBug_id());
        if (queryBug==null) return null;
        if(bug.getAssigned_to()!=null && assigned_toIsValidForUpdate(bug.getAssigned_to())) bug.setAssignDate(new java.sql.Date(System.currentTimeMillis()));
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
        if (deletedBug.getBug_id()==null) return null;

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



    public BugListResponse getByAssignee(Integer assignedTo) {
        if (assignedTo==null) return null;
        List<Bug> bugs =bugDao.getBugByAssignee(assignedTo);
        if (bugs.size()==0) return null;
        List<BugDto> bugDto= bugs.stream()
                .map(x->bugDtoMapper(x))
                .collect(Collectors.toList());
        return BugListResponse.builder().bugs(bugDto).build();


    }



    public BugListResponse getBugByCreatorId(Integer creator_id) {
        if (creator_id==null) return null;
        List<Bug> bugs =bugDao.getBugByCreatorId(creator_id);
        if (bugs.size()==0) return null;
        return BugListResponse.builder()
                .bugs(
                        bugs.stream()
                                .map(x->bugDtoMapper(x))
                                .collect(Collectors.toList()))
                .build();
    }

    private boolean titleIsValidForInsert(String title){
        if(title!=null && title.trim().length()>10 && title.trim().length()<=100) return true;
        else return false;
    }
    private boolean titleIsValidForUpdate(String title){
        if (title==null) return true;
        else if(title.trim().length()>10 && title.trim().length()<=100) return true;
        else return false;
    }

    public boolean descriptionIsValidForInsert(String description){
        if(description!=null && description.trim().length()>=50 && description.trim().length()<=1000) return true;
        return false;
    }

    public boolean urgencyIsValidForInsert(Integer urgency){
        if(urgency==null) return false;
        else if(urgency>=1 && urgency<=9) return true;
        else return false;
    }

    public boolean severityIsValidForInsert(Integer severity){
        if(severity==null) return false;
        else if(severity>=1 && severity<=9) return true;
        else return false;
    }
    public boolean idIsValidForUpdate(Integer bug_id){
        if (bug_id==null) return false;
        else return true;
    }

    public boolean assigned_toIsValidForUpdate(Integer assigned_to){
        if(userDao.getById(assigned_to)!=null) return true;
        else return false;
    }

    public boolean statusIsValidForUpdate(Integer status) {
        if(status==null) return true;
        else if(status>=1 && status<=4) return true;
        else return false;
    }

    public boolean creator_idIsValidForInsert(Integer creator_id){
        if(creator_id==null) return false;
        else if(userDao.getById(creator_id)!=null) return true;
        else return false;
    }


    public boolean descriptionIsValidForUpdate(String description){
        if (description==null) return true;
        if(description.trim().length()>=50 && description.trim().length()<=1000) return true;
        return false;
    }

    public boolean severityIsValidForUpdate(Integer severity){
        if(severity==null) return true;
        else if(severity>=1 && severity<=9) return true;
        else return false;
    }

    public boolean urgencyIsValidForUpdate(Integer urgency){
       if(urgency==null) return true;
        if(urgency>=1 && urgency<=9) return true;
        else return false;
    }
}

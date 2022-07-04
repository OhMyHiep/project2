package dao;

import dao.interfaces.BasicCrud;
import entity.Bug;
import org.jetbrains.annotations.NotNull;
import utils.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BugDaoImpl implements BasicCrud<Bug> {

    JDBCUtils jdbcUtils=new JDBCUtils();
    
    @Override
    public Bug getById(Integer id) {
        String sql= "SELECT *" +
                "FROM project2.bug " +
                "where bug_id=?";
        ResultSet rs= jdbcUtils.executeQuery(sql,id);
        return rowMapper(rs);
    }
    private Bug rowMapper (ResultSet rs){
           try {
               return rs.next()? Bug.builder()
                       .bug_id(rs.getInt("bug_id"))
                       .issueDate(rs.getDate("issue_date"))
                       .assignDate(rs.getDate("assign_date"))
                       .closeDate(rs.getDate("close_date"))
                       .assigned_to(rs.getInt("assigned_to"))
                       .creator_id(rs.getInt("creator_id"))
                       .description(rs.getString("description"))
                       .status(rs.getInt("status"))
                       .urgency(rs.getInt("urgency"))
                       .severity(rs.getInt("severity"))
                       .build(): null;
           }
           catch (SQLException e){
               System.out.println(e);
           }
           catch (Exception e){
               System.out.println(e);
           }
           return null;
    }

    @Override
    public List<Bug> getAll() {
        String sql = "SELECT *" +
                "FROM project2.bug;";
        ResultSet rs = jdbcUtils.executeQuery(sql);
        Bug bug= rowMapper(rs);
        ArrayList<Bug> bugs= new ArrayList<>();
        while (bug!=null) {
            bugs.add(bug);
            bug=rowMapper(rs);
        }
        return bugs;
    }

    @Override
    public Integer deleteById(Integer id) {
        String sql="DELETE FROM " +
                "project2.bug " +
                "WHERE bug_id=? RETURNING *";
        return rowMapper(jdbcUtils.executeQuery(sql,id)).getBug_id();
    }

    @Override
    public Integer insert(Bug bug) {
        String bug_id= bug.getBug_id()==null? "default": bug.getBug_id()+"";
        String sql= "INSERT INTO project2.bug " +
                "(bug_id,issue_date,assigned_to,creator_id,description,status,urgency,severity) " +
                "VALUES (?,?,?,?,?,?,?,?) RETURNING *";
        ResultSet rs = jdbcUtils.executeQuery(sql,
                Integer.parseInt(bug_id),
                bug.getIssueDate(),
                bug.getAssigned_to(),
                bug.getCreator_id(),
                bug.getDescription(),
                bug.getStatus(),
                bug.getUrgency(),
                bug.getSeverity()
                );
        Bug insertedBug= rowMapper(rs);
        return insertedBug.getBug_id();
    }

    @Override
    public Integer update(Bug bug) {
        String sql= "UPDATE project2.bug " +
                "SET assign_date=?,close_date=?, assigned_to=?,description=?,status=?,urgency=?,severity=? " +
                "WHERE bug_id=? " +
                "RETURNING *";

        return rowMapper(jdbcUtils.executeQuery(sql,
                bug.getAssignDate(),
                bug.getCloseDate(),
                bug.getAssigned_to(),
                bug.getDescription(),
                bug.getStatus(),
                bug.getUrgency(),
                bug.getSeverity(),
                bug.getBug_id())
        ).getBug_id();

    }

    public Bug getBugByUserId(Integer id){
        String sql= "SELECT *" +
                "FROM project2.bug " +
                "where creator_id=?";
        ResultSet rs= jdbcUtils.executeQuery(sql,id);
        return rowMapper(rs);
    }

    public void commitTransaction(){
        jdbcUtils.commit();
    }
    public void closeConnection(){
        jdbcUtils.close();
    }
    public Bug getBugByCreatorId(Integer creator_id) {
        String sql = "SELECT *" +
                "FROM project2.bug"+
                "where creator_id=?";

        ResultSet rs= jdbcUtils.executeQuery(sql,creator_id);
        return rowMapper(rs);
    }

    @NotNull
    private List<Bug> getBugs(Integer user_id, String sql) {
        ResultSet rs = jdbcUtils.executeQuery(sql,user_id);
        Bug bug= rowMapper(rs);
        ArrayList<Bug> bugs= new ArrayList<>();
        while (bug!=null) {
            bugs.add(bug);
            bug=rowMapper(rs);
        }
        return bugs;
    }


    public List<Bug> getBugByAssignee(Integer assignedTo) {
        String sql = "SELECT *" +
                "FROM project2.bug "+
                "WHERE assigned_to= ?";

        return getBugs(assignedTo,sql);
    }
}

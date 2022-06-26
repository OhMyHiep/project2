package dao;

import dao.interfaces.BasicCrud;
import entity.Bug;
import entity.BugComment;
import entity.User;
import utils.JDBCUtils;

import javax.swing.tree.RowMapper;
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
                "FROM bug;";

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
        String sql= "INSERT INTO project2.bug " +
                "(issue_date,assign_date,close_date,assigned_to,creator_id,description,status,urgency,severity) " +
                "VALUES (?,?,?,?,?,?,?,?,?) RETURNING *";
        ResultSet rs = jdbcUtils.executeQuery(sql,
                bug.getIssueDate(),
                bug.getAssignDate(),
                bug.getCloseDate(),
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
                "SET issue_date=?,assign_date=?,close_date=?, assigned_to=?,description=?,status=?,urgency=?,severity=? " +
                "WHERE bug_id=? " +
                "RETURNING *";

        return rowMapper(jdbcUtils.executeQuery(sql,
                bug.getIssueDate(),
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


    private BugComment commentRowMapper(ResultSet rs){
        try {
            if(rs.next())
                return new BugComment(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDate(5));
            else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void commitTransaction(){
        jdbcUtils.commit();
    }
    public void closeConnection(){
        jdbcUtils.close();
    }
    public List<Bug> getBugByCreatorId(Integer creator_id) {
        String sql = "SELECT *" +
                "FROM bug;"+
                "where creator_id=?";

        ResultSet rs = jdbcUtils.executeQuery(sql,creator_id);
        Bug bug= rowMapper(rs);
        ArrayList<Bug> bugs= new ArrayList<>();
        while (bug!=null) {
            bugs.add(bug);
            bug=rowMapper(rs);
        }
        return bugs;
    }

    @Override
    public List<Bug> getBugByAssignee(Integer assignedTo) {
        String sql = "SELECT *" +
                "FROM bug;"+
                "where assignedTo=?";

        ResultSet rs = jdbcUtils.executeQuery(sql,assignedTo);
        Bug bug= rowMapper(rs);
        ArrayList<Bug> bugs= new ArrayList<>();
        while (bug!=null) {
            bugs.add(bug);
            bug=rowMapper(rs);
        }
        return bugs;
    }
}

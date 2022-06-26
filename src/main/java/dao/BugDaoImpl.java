package dao;

import dao.interfaces.BasicCrud;
import entity.Bug;
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
                       .assignedTo(rs.getInt("assigned_to"))
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
        return null;
    }

    @Override
    public Integer insert(Bug bug) {
        String sql= "INSERT INTO project2.bug " +
                "(issue_date,assign_date,close_date,assigned_to,creator_id,description,status,urgency,severity) " +
                "(?,?,?,?,?,?,?,?,?) RETURNING bug_id";

        jdbcUtils.executeQuery(sql,
                bug.getIssueDate(),
                bug.getAssignDate(),
                bug.getCloseDate(),
                bug.getAssignedTo(),
                bug.getCreator_id(),
                bug.getDescription(),
                bug.getStatus(),
                bug.getUrgency(),
                bug.getSeverity()
                );
        return null;
    }

    @Override
    public Integer update(Bug bug) {
        String sql= "UPDATE project2.bug " +
                "SET issue_date=?,assign_date=?,close_date=?, assigned_to=?, creator_id=?,description=?,status=?,urgency=?,severity=?";

        return null;
    }
}

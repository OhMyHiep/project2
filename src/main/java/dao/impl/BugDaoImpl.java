package dao.impl;

import dao.BasicCrud;
import entity.Bug;
import entity.User;
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
                "FROM bug " +
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
        Bug firstBug= rowMapper(rs)==;
        ArrayList<Bug> bugs= new ArrayList<>();
        bugs.add(firstBug);


        return null;
    }

    @Override
    public Integer deleteById(Integer id) {
        return null;
    }

    @Override
    public Integer insert(User user) {
        return null;
    }

    @Override
    public Integer update(Integer id) {
        return null;
    }
}

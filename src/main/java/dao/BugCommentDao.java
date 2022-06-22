package dao;

import entity.BugComment;
import utils.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BugCommentDao implements BasicCrud<BugComment>{
    @Override
    public BugComment getById(Integer id) {
        JDBCUtils conn = new JDBCUtils();
        String sql = "SELECT * FROM project2.BugComment WHERE comment_id=?;";

        ResultSet rs = conn.executeQuery(sql, id);

        try {
            while (rs.next()) {
                return new BugComment(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDate(5));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            conn.close();
        }

        return null;
    }

    @Override
    public List<BugComment> getAll() {
        JDBCUtils conn = new JDBCUtils();
        String sql = "SELECT * FROM project2.BugComment";

        ResultSet rs = conn.executeQuery(sql);
        List<BugComment> listOfComments = new ArrayList<>();
        try {
            while (rs.next()) {
                listOfComments.add(new BugComment(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDate(5)));
            }

            return listOfComments;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            conn.close();
        }

        return null;
    }

    public List<BugComment> getAllByBugId(Integer id) {
        JDBCUtils conn = new JDBCUtils();
        String sql = "SELECT * FROM project2.BugComment WHERE bug_id=?";

        ResultSet rs = conn.executeQuery(sql,id);
        List<BugComment> listOfComments = new ArrayList<>();
        try {
            while (rs.next()) {
                listOfComments.add(new BugComment(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDate(5)));
            }

            return listOfComments;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            conn.close();
        }

        return null;
    }

    @Override
    public Integer deleteById(Integer id) {
        JDBCUtils conn = new JDBCUtils();
        String sql = "DELETE FROM project2.BugComment WHERE comment_id=?";

        Integer result = conn.executeUpdate(sql, id);

        conn.close();

        return result;
    }

    @Override
    public Integer insert(BugComment comment) {
        JDBCUtils conn = new JDBCUtils();
        String sql = "INSERT INTO project2.BugComment VALUES (default, ?, ?, ?, ?)";

        Integer result = conn.executeUpdate(sql, comment.getBugId(), comment.getCommenterUserId(), comment.getCommentText(), comment.getCommentDate());

        conn.close();

        return result;
    }

    // Will probably never be used, unless implement comment update but needs interface change
    @Override
    public Integer update(Integer id) {
        JDBCUtils conn = new JDBCUtils();
        String sql = "UPDATE project2.BugComment SET comment_text='' WHERE comment_id=?";

        Integer result = conn.executeUpdate(sql, id);

        conn.close();

        return result;
    }
}

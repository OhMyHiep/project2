package dao;

import dao.interfaces.BasicCrud;
import entity.User;
import utils.JDBCUtils;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements BasicCrud<User> {

    private JDBCUtils dbUtil = new JDBCUtils();

    @Override
    public User getById(Integer id) {//dan was here
        String qry = "SELECT * FROM project2.Users WHERE user_id=?;";

        ResultSet r = dbUtil.executeQuery(qry, id);

        User u = null;
        try {
            while (r.next()) {
                u = new User(r.getInt("user_id"),r.getString("username"),r.getString("passwd"),
                        r.getString("firstname"),r.getString("lastname"), r.getString("authtoken"));
            }
            return u;

        } catch (Exception e) {
            return u;
        }
    }

    @Override
    public List<User> getAll() {
        String qry = "SELECT * FROM project2.Users;";

        ArrayList<User> au = new ArrayList<User>();

        try {
            ResultSet rs = dbUtil.executeQuery(qry);

            while (rs.next()) {
                au.add(new User(rs.getInt("user_id"), rs.getString("username"),
                        rs.getString("passwd"), rs.getString("firstname"),
                        rs.getString("lastname"), rs.getString("authtoken")));
            }

            return au;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Integer deleteById(Integer id) {
        String qry = "DELETE FROM project2.Users WHERE user_id=? RETURNING user_id;";
        int output = -1;

        ResultSet rs = dbUtil.executeQuery(qry, id);

        try {
            while (rs.next()) {
                output = rs.getInt("user_id");
            }
            return output;
        } catch (Exception e) {
            return output;
        }
    }

    @Override
    public Integer insert(User data) {
        String qry = "INSERT INTO project2.Users(username,passwd,firstname,lastname,authtoken) VALUES (?, ?, ?, ?, ?) RETURNING user_id;";

        ResultSet rs = dbUtil.executeQuery(qry,data.getUsername(),data.getPasswd(),
                data.getFirstname(), data.getLastname(), data.getAuthToken());

        int output = -1;

        try {
            while(rs.next()) {
                output = rs.getInt("user_id");
            }
        } catch (Exception e) {}

        return output;
    }

    @Override
    public Integer update(User data) {
        String qry = "UPDATE TABLE project2.Users SET username=?, passwd=?, firstname=?, lastname=?, authtoken=? WHERE user_id=? RETURNING user_id;";
        int output = -1;

        ResultSet rs = dbUtil.executeQuery(qry, data.getUsername(), data.getPasswd(), data.getFirstname(), data.getLastname(),
                data.getAuthToken());

        try {
            while (rs.next()) {
                output = rs.getInt("user_id");
            }
        } catch (Exception e) {}

        return output;
    }

    public void closeConnection() {
        dbUtil.close();
    }

}

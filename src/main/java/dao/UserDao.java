package dao;

import dao.interfaces.BasicCrud;
import entity.User;
import utils.JDBCUtils;

import java.sql.ResultSet;
import java.util.List;

public class UserDao implements BasicCrud<User> {

    @Override
    public User getById(Integer id) {
        JDBCUtils dbUtil = new JDBCUtils();
        String qry = "SELECT * FROM project2.Users WHERE user_id=?;";

        ResultSet r = dbUtil.executeQuery(qry, id);

        User u = new User();
        try {
            while (r.next()) {
                u = new User(r.getString("user_id"),r.getString("username"),r.getString("passwd"),
                        r.getString("firstname"),r.getString("lastname"), r.getString("authtoken"));
            }
        } catch (Exception e) {}

        return u;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public Integer deleteById(Integer id) {
        return null;
    }

    @Override
    public Integer insert(User data) {
        return null;
    }

    @Override
    public Integer update(Object ...data) {

        return null;
    }
}

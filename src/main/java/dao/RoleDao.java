package dao;

import dao.interfaces.BasicCrud;
import entity.Role;
import utils.JDBCUtils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleDao implements BasicCrud<Role> {
    @Override
    public Role getById(Integer id) {
        JDBCUtils db = new JDBCUtils();
        String qry = "SELECT * FROM project2.roles WHERE role_id=?;";

        ResultSet r = db.executeQuery(qry, id);

        try {
            r.next();
            return new Role(r.getInt("role_id"),r.getString("role_title"));
        } catch (Exception e) {
            return null;
        } finally {
            db.close();
        }
    }

    public Role getByTitle(String title) {
        JDBCUtils db = new JDBCUtils();
        String qry = "SELECT * FROM project2.roles WHERE role_title=?;";

        ResultSet r = db.executeQuery(qry, title);

        try {
            r.next();
            return new Role(r.getInt("role_id"),r.getString("role_title"));
        } catch (Exception e) {
            return null;
        } finally {
            db.close();
        }
    }

    @Override
    public List<Role> getAll() {
        JDBCUtils db = new JDBCUtils();
        String qry = "SELECT * FROM project2.roles;";

        ResultSet r = db.executeQuery(qry);

        ArrayList<Role> out = new ArrayList<Role>();

        try {
            while (r.next()) {
                out.add(new Role(r.getInt("role_id"),r.getString("role_title")));
            }
            return out;
        } catch (Exception e) {
            return null;
        } finally {
            db.close();
        }
    }

    @Override
    public Integer deleteById(Integer id) {
        JDBCUtils db = new JDBCUtils();
        String qry = "DELETE FROM project2.roles WHERE role_id=? RETURNING role_id;";

        ResultSet r = db.executeQuery(qry, id);

        try {
            r.next();
            return r.getInt("role_id");
        } catch (Exception e) {
            return null;
        } finally {
            db.close();
        }
    }

    @Override
    public Integer insert(Role data) {
        JDBCUtils db = new JDBCUtils();
        String qry = "INSERT INTO project2.roles(role_id, role_title) VALUES (?,?) RETURNING role_id;";

        ResultSet r = db.executeQuery(qry, data.getRole_id(), data.getRole_title());

        try {
            r.next();
            return r.getInt("role_id");
        } catch (Exception e) {
            return null;
        } finally {
            db.close();
        }
    }

    @Override
    public Integer update(Role data) {
        JDBCUtils db = new JDBCUtils();

        String qry = "UPDATE project2.roles "+
                        "SET role_title=? " +
                        "WHERE role_id=? RETURNING role_id;";

        ResultSet r = db.executeQuery(qry,data.getRole_title(),data.getRole_id());

        try {
            r.next();
            return r.getInt("role_id");
        } catch (Exception e) {
            return null;
        } finally {
            db.close();
        }
    }
}

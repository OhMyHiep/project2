package dao;

import utils.Cryptographer;
import utils.JDBCUtils;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class AuthenticationDao {

    public static String authenticate(String username, String password) {
        JDBCUtils dbUtil = new JDBCUtils();
        String qry = "SELECT * FROM project2.Users WHERE username=? AND passwd=?;";
        String hashedPass = Cryptographer.MD5(password);

        ResultSet r = dbUtil.executeQuery(qry, username, hashedPass);

        ArrayList<String> results = new ArrayList<String>();

        try {
            while (r.next()) {
                results.add(r.getString(5));
            }
        } catch (Exception e) {}

        if (results.size() == 1) {
            Random rand = new Random();
            return Cryptographer.MD5(Integer.valueOf(rand.nextInt()).toString()+
                    LocalDateTime.now().toString());
        } else {
            return null;
        }

    }

}

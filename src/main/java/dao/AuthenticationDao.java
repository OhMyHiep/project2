package dao;

import org.jetbrains.annotations.Nullable;
import utils.Cryptographer;
import utils.JDBCUtils;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class AuthenticationDao {

    @Nullable
    public static String authenticate(String username, String password) {
        JDBCUtils dbUtil = new JDBCUtils();
        String qry = "SELECT * FROM project2.Users WHERE username=? AND passwd=?;";
        String hashedPass = Cryptographer.MD5(password);

        ResultSet r = dbUtil.executeQuery(qry, username, hashedPass);

        ArrayList<String> results = new ArrayList<String>();

        try {
            while (r.next()) {
                results.add(r.getString(0));
            }
        } catch (Exception e) {}

        String qry2 = "UPDATE project2.Users SET authtoken=? WHERE user_id=?;";

        if (results.size() == 1) {
            Random rand = new Random();
            String authToken = Cryptographer.MD5(Integer.valueOf(rand.nextInt()).toString()+
                    LocalDateTime.now().toString());

            try {

                dbUtil.executeQuery(qry2, authToken, results.get(0));

            } catch(Exception e) {
                return null;
            }

            return authToken;
        } else {
            return null;
        }

    }

}

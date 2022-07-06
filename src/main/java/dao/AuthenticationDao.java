package dao;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.jetbrains.annotations.Nullable;
import utils.Constants;
import utils.Cryptographer;
import utils.JDBCUtils;

import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class AuthenticationDao {

    @Nullable
    public String authenticate(String username, String password) {
        JDBCUtils dbUtil = new JDBCUtils();
        String qry = "SELECT * FROM project2.Users WHERE username=? AND passwd=?;";
//        String hashedPass = Cryptographer.MD5(password);

        String roles = "";

        ResultSet r = dbUtil.executeQuery(qry, username, password);
        ArrayList<String> results = new ArrayList<String>();
//        System.out.println("result has next");
        try {
            while (r.next()) {
                results.add(r.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        if (results.size() > 0) {
            String qry2 = "select r.role_title " +
                    "from project2.users u join project2.userroles ur on ur.user_id = u.user_id " +
                    "join project2.roles r on r.role_id = ur.role_id " +
                    "where username = ?;";

            ResultSet r2 = dbUtil.executeQuery(qry2, username);


            try {
                while (r2.next()) {
                    roles = roles + r2.getString(1) + ",";
                }
            } catch (Exception e) {}


        } else {
            return null;
        }

        return roles;
    }



}

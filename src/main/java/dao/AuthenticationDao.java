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

        if (results.size() == 1) {
            String qry2 = "SELECT role_title FROM "+
                            "((project2.users INNER JOIN project2.userroles " +
                            "ON project2.users.user_id = project2.userroles.user_id) " +
                            "AS stuff" +
                            "INNER JOIN project2.roles ON project2.roles.role_id = stuff.role_id)" +
                            "WHERE project2.users.username = ?;";

            ResultSet r2 = dbUtil.executeQuery(qry2, username);
            String roles = "";

            try {
                while (r2.next()) {
                    roles = roles + r2.getString(0) + ",";
                }
            } catch (Exception e) {}

            Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(Constants.secretKey),
                    SignatureAlgorithm.HS256.getJcaName());

            Instant now = Instant.now();
            String jwtToken = Jwts.builder()
                    .claim("username", username)
                    .claim("roles", roles)
                    .setSubject("JWT Auth")
                    .setId(UUID.randomUUID().toString())
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(now.plus(50, ChronoUnit.MINUTES)))
                    .signWith(hmacKey)
                    .compact();

            return jwtToken;
        } else {
            return null;
        }

    }

}

package dao;

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
            Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(Constants.secretKey),
                    SignatureAlgorithm.HS256.getJcaName());

            Instant now = Instant.now();
            String jwtToken = Jwts.builder()
                    .claim("username", username)
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

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
            String qry2 = "SELECT role_id FROM project2.userroles WHERE";

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


    public static Jws<Claims> parseJwt(String jwtString) {
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(jwtString);

        return jwt;
    }

}

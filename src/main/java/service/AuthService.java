package service;

import dao.AuthenticationDao;
import dao.RoleDao;
import dao.UserDao;
import entity.Role;
import entity.User;
import io.jsonwebtoken.*;
import utils.Constants;
import utils.Tuple;

import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Array;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class AuthService {

    public Jws<Claims> decryptJwt(String jwtString) {
        String secret = Constants.secretKey;
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(jwtString);

        return jwt;
    }

    public Tuple<ArrayList<Role>,User> decodeJwt(Jws<Claims> claims) {
        String user_id = claims.getBody().get("user_id").toString();
        String roleString = claims.getBody().get("roles").toString();

        String[] roleSplit = roleString.split(",");

        RoleDao rd = new RoleDao();
        ArrayList<Role> possibleRoles = (ArrayList<Role>) rd.getAll();

        ArrayList<Role> chosenRoles = new ArrayList<Role>();

        for (String r : roleSplit) {
            for (Role r2 : possibleRoles) {
                if (r.toLowerCase().equals(r2.getRole_title().toString().toLowerCase())) {
                    chosenRoles.add(r2);
                }
            }
        }

        UserDao ud = new UserDao();
        User theUser = ud.getById(Integer.valueOf(user_id));



        return new Tuple<ArrayList<Role>,User>(chosenRoles,theUser);
    }


    public String buildJwt(Tuple<ArrayList<Role>, User> data) {

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(Constants.secretKey),
                SignatureAlgorithm.HS256.getJcaName());

        ArrayList<Role> roles = data.x;
        int user_id = data.y.getUser_id();

        String roleString = "";

        for (Role role : roles) {
            roleString = roleString + role.getRole_title() + ",";
        }


        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .claim("user_id", user_id)
                .claim("roles", roleString)
                .setSubject("JWT Auth")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(50, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();

        return jwtToken;

    }

    public AuthResponse buildJwt(AuthRequest login) {
        String username = login.getUsername();
        String pw = login.getPassword();

        String roleString = AuthenticationDao.authenticate(username,pw);
        String[] splitted = roleString.split(",");

        ArrayList<Role> roles = new ArrayList<>();
        RoleDao rd = new RoleDao();

        for (String s: splitted) {
            roles.add(rd.getByTitle(s));
        }

        List<RoleDto> RoleDtolist = roles.stream()
                .map(role->RoleService.roleDtoMapper(role))
                .collect(Collectors.toList());

        UserDao ud = new UserDao();
        User u = ud.getByCredentials(username,pw);

        String token = buildJwt(new Tuple<ArrayList<Role>,User>(roles,u));
        UserResponse ur=userService.getUserResponse(u);
        ur.setRoles(RoleDtolist);
        AuthResponse ar = new AuthResponse();
        ar.setUser(ur);
        ar.setToken(token);
        return ar;
    }


    public Tuple<ArrayList<Role>,User> ddJwt(String token) {
        return decodeJwt(decryptJwt(token));
    }
}

package service;

import dao.AuthenticationDao;
import dao.RoleDao;
import dao.UserDao;
import domain.repsonse.AuthResponse;
import domain.repsonse.BugListResponse;
import domain.repsonse.UserResponse;
import domain.request.AuthRequest;
import entity.Role;
import entity.User;
import entity.dto.RoleDto;
import io.jsonwebtoken.*;
import utils.Constants;
import utils.Tuple;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class AuthService {

    private RoleDao rd = new RoleDao();
    private UserDao ud = new UserDao();
    private UserService userService = new UserService();

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

        ArrayList<Role> possibleRoles = (ArrayList<Role>) rd.getAll();
        ArrayList<Role> chosenRoles = new ArrayList<Role>();

        for (String r : roleSplit) {
            for (Role r2 : possibleRoles) {
                if (r.equalsIgnoreCase(r2.getRole_title().toString())) {
                    chosenRoles.add(r2);
                }
            }
        }
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
        if (roleString==null) return null;
        String[] splitted = roleString.split(",");

        ArrayList<Role> roles = new ArrayList<>();

        for (String s: splitted) {
            roles.add(rd.getByTitle(s));
        }

        List<RoleDto> RoleDtolist = roles.stream()
                .map(role->RoleService.roleDtoMapper(role))
                .collect(Collectors.toList());
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

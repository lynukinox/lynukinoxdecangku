package dorm.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "dorm-repair-system-secret-key-2024-springboot";
    private static final long EXPIRE = 7 * 24 * 60 * 60 * 1000;

    private static Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public static String generateToken(Integer userId, String username, String role) {
       return Jwts.builder()
               .claim("userId",userId)
               .claim("username",username)
               .claim("role",role)
               .setIssuedAt(new Date())
               .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
               .signWith(getKey(),SignatureAlgorithm.HS256)
               .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Integer getUserId(String token) {
        return parseToken(token).get("userId", Integer.class);
    }

    // 【重点13】从 Token 中获取角色
    public static String getRole(String token) {
        return parseToken(token).get("role", String.class);
    }
}
package org.example.aad_final_project.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.aad_final_project.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@PropertySource(ignoreResourceNotFound = true, value = "classpath:otherprops.properties")
public class JwtUtil implements Serializable {

    private static final long serialVersionUID = 234234523523L;
    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 12;

    @Value("${jwt.secret}")
    private String secretKey;

    public String getUsernameFromToken(String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                throw new IllegalArgumentException("Token cannot be null or empty");
            }
            Claims claims = getAllClaimsFromToken(token);
            return claims.getSubject();
        } catch (Exception e) {
            System.err.println("Error extracting username from token: " + e.getMessage());
            return null;
        }
    }

    public Claims getUserRoleCodeFromToken(String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                throw new IllegalArgumentException("Token cannot be null or empty");
            }
            return getAllClaimsFromToken(token);
        } catch (Exception e) {
            System.err.println("Error extracting claims from token: " + e.getMessage());
            return null;
        }
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.err.println("Error parsing token: " + e.getMessage());
            throw new RuntimeException("Invalid token", e);
        }
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDTO userDTO) {
        if (userDTO == null || userDTO.getEmail() == null || userDTO.getRole() == null) {
            throw new IllegalArgumentException("User information is missing");
        }

        System.out.println("Generating token for user: " + userDTO.getEmail());
        System.out.println("Assigned Role: " + userDTO.getRole());

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDTO.getRole());

        String token = doGenerateToken(claims, userDTO.getEmail());

        System.out.println("Generated JWT Token: " + token);
        return token;
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = getUsernameFromToken(token);
            System.out.println("Validating token for: " + username);
            return (username != null &&
                    username.equals(userDetails.getUsername()) &&
                    !isTokenExpired(token));
        } catch (Exception e) {
            System.err.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }
}



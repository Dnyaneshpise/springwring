package com.learn.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {


    private static final String SECRET = "for-demo-i-am-writing here";
    private static final long EXPIRY_MS = 1000 * 60 * 60;  // 1 hour in milliseconds

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Generate a token for a user
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)               // "sub" claim — who this token is for
                .claim("role", role)                // custom claim
                .setIssuedAt(new Date())            // "iat" — when issued
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_MS))  // "exp"
                .signWith(getSigningKey())          // sign with secret
                .compact();                         // build the string
    }

    // Extract username from token
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // Extract role from token
    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // Validate token — returns false if expired or tampered
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);   // throws exception if invalid
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;         // expired, tampered, malformed
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())     // verify with same secret
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
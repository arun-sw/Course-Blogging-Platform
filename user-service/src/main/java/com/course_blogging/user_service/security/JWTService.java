package com.course_blogging.user_service.security;

import com.course_blogging.user_service.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;

import java.util.Base64;
import java.util.Date;
@Service
public class JWTService {
    private final SecretKey key;
    private final long expirancetime;
    public JWTService(@Value("${security.jwt.secret-key}") String secret,
            @Value("${security.jwt.expiration-ms}") long expirancetime) {
        this.key = Keys.hmacShaKeyFor(
                Base64.getDecoder().decode(secret)
        );
        this.expirancetime = expirancetime;
    }
    // Generate JWT Token
    public String GenerateToken(UserEntity user) {
        Date now = new Date();
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("user ID:", user.getUserId())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirancetime))
                .signWith(key)
                .compact();
    }
    // Read JWT Claims
    public Claims claims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
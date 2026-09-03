package com.course_blogging.blog_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class JWTService {

    private final SecretKey key;

    public JWTService(
            @Value("${security.jwt.secret-key}") String secret) {

        this.key = Keys.hmacShaKeyFor(
                Base64.getDecoder().decode(secret)
        );
    }
    public Claims validateToken(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
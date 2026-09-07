package com.course_blogging.notification_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;

@Service
public class JWTService {
    private final SecretKey key;

    public JWTService(@Value("${security.jwt.secret-key}") String secret) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    public Long authenticatedUserId(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        Object userId = claims.get("user ID:");
        if (!(userId instanceof Number)) throw new IllegalArgumentException("Token does not contain a user ID");
        return ((Number) userId).longValue();
    }
}

package io.clementleetimfu.educationmanagementsystem.utils.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private SecretKey key;

    private static final long EXPIRATION_MS = 60 * 60 * 1000; // 1 hour

    public JwtUtil(@Value("${auth.jwt.secretKey}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String generateToken(Integer employeeId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_MS);
        return Jwts.builder()
                .issuedAt(now)
                .expiration(expiryDate)
                .subject(String.valueOf(employeeId))
                .signWith(key)
                .compact();
    }

    public Integer parseToken(String token) {
        String subject = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return Integer.parseInt(subject);
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            log.warn("Invalid token:{}", token, e);
            return false;
        }
    }
}
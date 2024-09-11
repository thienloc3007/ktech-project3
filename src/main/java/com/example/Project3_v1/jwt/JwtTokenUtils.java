package com.example.Project3_v1.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtils {
    private final Key secretKey;
    private final JwtParser jwtParser;

    public JwtTokenUtils(
            @Value("${jwt.secret}")
            String jwtSecret
    ) {
        this.secretKey =
                Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(this.secretKey)
                .build();
    }

    public String generateToken(UserDetails userDetails) {
        // JWT를 만들때는 보통 3가지 정보를 포함시킨다.
        // iat: issued at
        // sub: 대상자
        // exp: expire at
        Instant now = Instant.now();
        // JWT의 payload (body)에 포함되는 데이터는 claim이라고 부른다.
        Claims jwtClaims = Jwts.claims()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(120)));

        return Jwts.builder()
                .setClaims(jwtClaims)
                .signWith(secretKey)
                .compact();
    }

    public boolean validate(String token) {
        try {
            Claims claims = jwtParser.parseClaimsJws(token)
                    .getBody();
            log.info("sub: {}", claims.getSubject());
            log.info("iat: {}", claims.getIssuedAt());
            log.info("exp: {}", claims.getExpiration());
            return true;
        } catch (Exception e) {
            log.warn("invalid jwt provided: {}", e.getMessage());
        }
        return false;
    }

    public Claims parseClaims(String token) {
        return jwtParser.parseClaimsJws(token)
                .getBody();
    }
}
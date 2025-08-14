package com.example.auth_service.services.drivers;

import com.example.auth_service.models.User;
import com.example.auth_service.services.dto.AccessTokenPayload;
import com.example.auth_service.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService implements TokenService {

    @Autowired
    private Environment env;

    @Override
    public String createAccessToken(User user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("roles", user.getRoles())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + getAccessTokenExpireMs()))
            .signWith(Keys.hmacShaKeyFor(getSecret().getBytes()), SignatureAlgorithm.HS256)
            .compact();
    }

    @Override
    public String createRefreshToken(User user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("token_type", "refresh_token")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + getRefreshTokenExpireMs()))
            .signWith(Keys.hmacShaKeyFor(getSecret().getBytes()), SignatureAlgorithm.HS256)
            .compact();
    }

    @Override
    public AccessTokenPayload verifyAccessToken(String token) throws Exception {
        var claims = Jwts.parserBuilder()
            .setSigningKey(getSecret().getBytes())
            .build()
            .parseClaimsJws(token);

        return new JwtAccessTokenPayload(claims);
    }

    @Override
    public void verifyRefreshToken(String refreshToken) throws Exception {
        Jwts.parserBuilder()
            .setSigningKey(getSecret().getBytes())
            .build()
            .parseClaimsJws(refreshToken);
    }

    private String getSecret() {
        return env.getProperty("spring.application.token-service.jwt.secret");
    }

    private Long getAccessTokenExpireMs() {
        return env.getProperty(
            "spring.application.token-service.access-token-exp-ms", Long.class);
    }

    private Long getRefreshTokenExpireMs() {
        return env.getProperty(
            "spring.application.token-service.refresh-token-exp-ms", Long.class);
    }
}

package com.example.auth_service.services.drivers.token_service;

import com.example.auth_service.models.User;
import com.example.auth_service.services.dto.token_service.AccessTokenPayload;
import com.example.auth_service.services.TokenService;
import com.example.auth_service.services.dto.token_service.RefreshTokenPayload;
import com.example.auth_service.services.exceptions.token_service.InvalidTokenException;
import com.example.auth_service.services.exceptions.token_service.TokenExpiredException;
import com.example.auth_service.services.exceptions.token_service.TokenRejectedException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

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
            .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpireMs()))
            .signWith(Keys.hmacShaKeyFor(secret().getBytes()), SignatureAlgorithm.HS256)
            .compact();
    }

    @Override
    public String createRefreshToken(User user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("token_type", "refresh_token")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpireMs()))
            .signWith(Keys.hmacShaKeyFor(secret().getBytes()), SignatureAlgorithm.HS256)
            .compact();
    }

    @Override
    public AccessTokenPayload verifyAccessToken(String accessToken) throws InvalidTokenException, TokenExpiredException, TokenRejectedException {
        var claims = parseJwt(accessToken);
        return new JwtAccessTokenPayload(claims);
    }

    @Override
    public RefreshTokenPayload verifyRefreshToken(
        String accessToken,
        String refreshToken
    ) throws InvalidTokenException, TokenExpiredException, TokenRejectedException {
        var accessTokenPayload = new JwtRefreshTokenPayload(parseJwt(accessToken));
        var refreshTokenPayload = new JwtRefreshTokenPayload(parseJwt(refreshToken));
        // Token type is not "refresh_token"
        if (! refreshTokenPayload.verifyTokenType()) {
            throw new InvalidTokenException();
        }
        // AccessToken and RefreshToken subject mismatch
        if (! Objects.equals(accessTokenPayload.getSubject(), refreshTokenPayload.getSubject())) {
            throw new InvalidTokenException();
        }
        return refreshTokenPayload;
    }

    private Jws<Claims> parseJwt(String token) throws InvalidTokenException, TokenExpiredException, TokenRejectedException {
        try {
            return Jwts.parserBuilder().setSigningKey(secret().getBytes()).build().parseClaimsJws(token);
        } catch (ExpiredJwtException ex) {
            throw new TokenExpiredException();
        } catch (SignatureException ex) {
            throw new TokenRejectedException();
        } catch (Exception ex) {
            throw new InvalidTokenException();
        }
    }

    private String secret() {
        return env.getProperty("spring.application.token-service.jwt.secret");
    }

    private Long accessTokenExpireMs() {
        return env.getProperty("spring.application.token-service.access-token-exp-ms", Long.class);
    }

    private Long refreshTokenExpireMs() {
        return env.getProperty("spring.application.token-service.refresh-token-exp-ms", Long.class);
    }
}

package com.example.auth_service.services.drivers.token_service;

import com.example.auth_service.services.dto.token_service.RefreshTokenPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.Objects;

public class JwtRefreshTokenPayload implements RefreshTokenPayload {
    private final Jws<Claims> claims;

    public JwtRefreshTokenPayload(Jws<Claims> claims) {
        this.claims = claims;
    }

    @Override
    public String getSubject() {
        return claims.getBody().getSubject();
    }

    @Override
    public String getTokenType() { return claims.getBody().get("token_type", String.class); }

    @Override
    public Boolean verifyTokenType() {
        return Objects.equals(getTokenType(), "refresh_token");
    }
}

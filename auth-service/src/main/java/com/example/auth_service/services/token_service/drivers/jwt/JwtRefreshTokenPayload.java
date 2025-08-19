package com.example.auth_service.services.token_service.drivers.jwt;

import com.example.auth_service.services.token_service.dto.RefreshTokenPayload;
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

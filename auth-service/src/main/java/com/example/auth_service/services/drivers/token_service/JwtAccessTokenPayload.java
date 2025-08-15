package com.example.auth_service.services.drivers.token_service;

import com.example.auth_service.services.dto.token_service.AccessTokenPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class JwtAccessTokenPayload implements AccessTokenPayload {
    private final Jws<Claims> claims;

    public JwtAccessTokenPayload(Jws<Claims> claims) {
        this.claims = claims;
    }

    @Override
    public String getSubject() {
        return claims.getBody().getSubject();
    }
}

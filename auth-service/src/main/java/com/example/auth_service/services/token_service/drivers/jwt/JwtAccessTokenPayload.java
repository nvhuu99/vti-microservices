package com.example.auth_service.services.token_service.drivers.jwt;

import com.example.auth_service.services.token_service.dto.AccessTokenPayload;
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

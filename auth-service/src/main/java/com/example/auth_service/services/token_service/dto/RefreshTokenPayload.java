package com.example.auth_service.services.token_service.dto;

public interface RefreshTokenPayload {
    String getSubject();
    String getTokenType();
    Boolean verifyTokenType();
}

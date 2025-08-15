package com.example.auth_service.services.dto.token_service;

public interface RefreshTokenPayload {
    String getSubject();
    String getTokenType();
    Boolean verifyTokenType();
}

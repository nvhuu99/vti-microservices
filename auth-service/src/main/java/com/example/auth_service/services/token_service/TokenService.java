package com.example.auth_service.services.token_service;

import com.example.auth_service.models.User;
import com.example.auth_service.services.token_service.dto.AccessTokenPayload;
import com.example.auth_service.services.token_service.dto.RefreshTokenPayload;
import com.example.auth_service.services.token_service.exceptions.InvalidTokenException;
import com.example.auth_service.services.token_service.exceptions.TokenExpiredException;
import com.example.auth_service.services.token_service.exceptions.TokenRejectedException;

public interface TokenService {
    String createAccessToken(User user);

    String createRefreshToken(User user);

    AccessTokenPayload verifyAccessToken(String token) throws InvalidTokenException, TokenExpiredException, TokenRejectedException;

    RefreshTokenPayload verifyRefreshToken(String accessToken, String refreshToken) throws InvalidTokenException, TokenExpiredException, TokenRejectedException;
}

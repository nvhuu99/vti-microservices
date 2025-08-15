package com.example.auth_service.services;

import com.example.auth_service.models.User;
import com.example.auth_service.services.dto.token_service.AccessTokenPayload;
import com.example.auth_service.services.dto.token_service.RefreshTokenPayload;
import com.example.auth_service.services.exceptions.token_service.InvalidTokenException;
import com.example.auth_service.services.exceptions.token_service.TokenExpiredException;
import com.example.auth_service.services.exceptions.token_service.TokenRejectedException;

public interface TokenService {
    public String createAccessToken(User user);

    public String createRefreshToken(User user);

    public AccessTokenPayload verifyAccessToken(String token) throws InvalidTokenException, TokenExpiredException, TokenRejectedException;

    public RefreshTokenPayload verifyRefreshToken(String accessToken, String refreshToken) throws InvalidTokenException, TokenExpiredException, TokenRejectedException;
}

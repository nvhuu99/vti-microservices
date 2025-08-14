package com.example.auth_service.services;

import com.example.auth_service.models.User;
import com.example.auth_service.services.dto.AccessTokenPayload;
import org.springframework.stereotype.Service;

public interface TokenService {
    public String createAccessToken(User user);
    public String createRefreshToken(User user);
    public AccessTokenPayload verifyAccessToken(String token) throws Exception;
    public void verifyRefreshToken(String token) throws Exception;
}

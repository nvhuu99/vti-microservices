package com.example.api_gateway.services.user_service;

import com.example.api_gateway.models.User;
import com.example.api_gateway.services.user_service.exceptions.NotFoundException;

public interface UserService {
    User findByAccessToken(String accessToken);
    void setAccessToken(String username, String accessToken) throws NotFoundException;
    void unsetAuthTokens(String username);
}

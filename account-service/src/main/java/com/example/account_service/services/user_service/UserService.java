package com.example.account_service.services.user_service;

import com.example.account_service.models.User;
import com.example.account_service.services.user_service.dto.SaveUser;
import com.example.account_service.services.user_service.exceptions.NotFoundException;

public interface UserService {
    void setAuthTokens(String username, String accessToken, String refreshToken) throws NotFoundException;
    void unsetAuthTokens(String accessToken);
}

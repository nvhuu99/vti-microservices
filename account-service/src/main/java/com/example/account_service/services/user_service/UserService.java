package com.example.account_service.services.user_service;

import com.example.account_service.models.User;
import com.example.account_service.services.user_service.dto.SaveUser;

public interface UserService {
    void setAuthTokens(String username, String accessToken, String refreshToken) throws Exception;
}

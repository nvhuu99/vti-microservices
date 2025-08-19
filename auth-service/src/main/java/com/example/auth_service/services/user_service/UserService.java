package com.example.auth_service.services.user_service;

import com.example.auth_service.models.User;
import com.example.auth_service.services.user_service.dto.RegisterUser;
import com.example.auth_service.services.user_service.exceptions.BadCredentialsException;
import com.example.auth_service.services.user_service.exceptions.EmailDuplicationException;
import com.example.auth_service.services.user_service.exceptions.UsernameDuplicationException;

public interface UserService {
    User findByUsername(String username);
    User authenticate(String username, String password) throws BadCredentialsException;
    User register(RegisterUser data) throws UsernameDuplicationException, EmailDuplicationException;
}

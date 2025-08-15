package com.example.auth_service.services;

import com.example.auth_service.models.User;
import com.example.auth_service.services.dto.user_service.RegisterUser;
import com.example.auth_service.services.exceptions.user_service.BadCredentialsException;
import com.example.auth_service.services.exceptions.user_service.UsernameDuplicationException;

public interface UserService {
    User findByUsername(String username);
    User findByEmail(String email);
    User authenticate(String username, String password) throws BadCredentialsException;
    User register(RegisterUser data) throws UsernameDuplicationException;
}

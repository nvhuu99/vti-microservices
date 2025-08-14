package com.example.auth_service.services;

import com.example.auth_service.models.User;
import com.example.auth_service.services.dto.SaveUser;
import com.example.auth_service.services.exceptions.BadCredentialsException;
import com.example.auth_service.services.exceptions.UsernameDuplicationException;

public interface UserService {
    User login(String username, String password) throws BadCredentialsException;
    User register(SaveUser data) throws UsernameDuplicationException;
}

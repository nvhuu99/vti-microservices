package com.example.auth_service.services.dto.user_service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AuthenticateUser {
    @NotNull(message = "Username is required")
    @Length(min = 2, max = 50, message = "Username must be between 2 - 50 characters")
    String username;

    @NotNull(message = "Password is required")
    @Length(min = 3, max = 30, message = "Password must be between 2 - 50 characters")
    String password;
}

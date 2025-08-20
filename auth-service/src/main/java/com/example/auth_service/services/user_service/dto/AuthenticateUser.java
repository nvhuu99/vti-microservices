package com.example.auth_service.services.user_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AuthenticateUser {
    @NotEmpty(message = "Username is required")
    @Length(min = 2, max = 50, message = "Username must be between 2 - 50 characters")
    String username;

    @NotEmpty(message = "Password is required")
    @Length(min = 3, max = 30, message = "Password must be between 2 - 50 characters")
    String password;
}

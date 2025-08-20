package com.example.auth_service.services.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterUser {
    @NotNull(message = "Username is required")
    @Length(min = 2, max = 50, message = "Username must be between 2 - 50 characters")
    String username;

    @Email(message = "Invalid email address")
    @Length(min = 3, max = 255, message = "Email must be between 3 - 255 characters")
    String email;

    @NotNull(message = "Password is required")
    @Length(min = 3, max = 30, message = "Password must be between 3 - 50 characters")
    String password;
}
package com.example.auth_service.api.requests;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
}

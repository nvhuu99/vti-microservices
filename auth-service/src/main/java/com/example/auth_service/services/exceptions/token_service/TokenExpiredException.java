package com.example.auth_service.services.exceptions.token_service;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException() {
        super("Token expired");
    }
}

package com.example.auth_service.services.exceptions.token_service;

public class TokenRejectedException extends RuntimeException {
    public TokenRejectedException() {
        super("Token rejected");
    }
}

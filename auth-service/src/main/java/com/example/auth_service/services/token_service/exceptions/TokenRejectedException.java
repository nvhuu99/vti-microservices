package com.example.auth_service.services.token_service.exceptions;

public class TokenRejectedException extends RuntimeException {
    public TokenRejectedException() {
        super("Token rejected");
    }
}

package com.example.auth_service.api.exceptions;

import jakarta.servlet.ServletException;

public class AuthenticationFailureException extends ServletException {
    public AuthenticationFailureException() { super("Authentication failure"); }
}

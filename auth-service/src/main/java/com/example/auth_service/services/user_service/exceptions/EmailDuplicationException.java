package com.example.auth_service.services.user_service.exceptions;

public class EmailDuplicationException extends Exception {
    public EmailDuplicationException() { super("Email has already taken"); }
}

package com.example.auth_service.services.user_service.exceptions;

public class UsernameDuplicationException extends Exception {
    public UsernameDuplicationException() { super("Username has already existed"); }
}

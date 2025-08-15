package com.example.auth_service.services.exceptions.user_service;

public class UsernameDuplicationException extends Exception {
    public UsernameDuplicationException() { super("Username has already existed"); }
}

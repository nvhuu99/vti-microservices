package com.example.auth_service.services.user_service.user_service;

public class UsernameDuplicationException extends Exception {
    public UsernameDuplicationException() { super("Username has already existed"); }
}

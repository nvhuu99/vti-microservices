package com.example.auth_service.services.exceptions.user_service;

public class NotFoundException extends Exception {
    public NotFoundException(String target, String value) {
        super(target + " - " + value + ": does not exist");
    }
}

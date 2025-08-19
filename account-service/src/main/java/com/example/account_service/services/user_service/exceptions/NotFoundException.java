package com.example.account_service.services.user_service.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String target, String value) {
        super(target + " - " + value + ": does not exist");
    }
}

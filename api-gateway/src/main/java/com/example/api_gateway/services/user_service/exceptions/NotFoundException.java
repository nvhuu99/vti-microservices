package com.example.api_gateway.services.user_service.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String target, String value) {
        super(target + " - " + value + ": does not exist");
    }
}

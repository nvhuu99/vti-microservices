package com.example.account_service.app.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnhandledException extends RuntimeException {

    private HttpStatus responseCode;
    private String errorMessage;

    public UnhandledException() {
        super();
    }

    public UnhandledException(HttpStatus responseCode, String errorMessage) {
        super();
        this.responseCode = responseCode;
        this.errorMessage = errorMessage;
    }
}

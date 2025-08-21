package com.example.account_service.app.exceptions;

import com.example.account_service.utils.UrlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private UrlBuilder urlBuilder;

    @ExceptionHandler(value = UnhandledException.class)
    public String handleUnhandledException(UnhandledException exception) {
        return "redirect:" + urlBuilder.build("account/error", Map.of(
            "status", exception.getResponseCode(),
            "message", exception.getErrorMessage()
        ));
    }

    @ExceptionHandler(value = MissingRequestValueException.class)
    public String handleMissingRequestParam(MissingRequestValueException exception) {
        return "redirect:" + urlBuilder.build("account/error", Map.of(
            "status", HttpStatus.BAD_REQUEST,
            "message", "Required parameters missing."
        ));
    }
}

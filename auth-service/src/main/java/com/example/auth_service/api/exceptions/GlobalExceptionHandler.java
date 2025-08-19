package com.example.auth_service.api.exceptions;

import com.example.auth_service.api.responses.ApiResponse;
import com.example.auth_service.services.user_service.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AuthenticationFailureException.class)
    public ResponseEntity<ApiResponse> handleAuthFailure(AuthenticationFailureException exception) {
        return ApiResponse.unAuthorized();
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFound(NotFoundException exception) {
        return ApiResponse.notFound(exception.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleInvalidArg(MethodArgumentNotValidException exception) {
        var errs = new HashMap<String, String>();
        for (var err: exception.getAllErrors()) {
            try {
                errs.put(((FieldError)err).getField(), err.getDefaultMessage());
            } catch (Exception ignored) {
            }
        }
        return ApiResponse.badRequest("Invalid arguments", errs);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> handleUnknownException(Exception exception) {
        return ApiResponse.internalServerError("Internal Server Error");
    }
}

package com.example.department_service.api.exceptions;

import com.example.department_service.api.responses.ApiResponse;
import com.example.department_service.services.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException exception) {
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

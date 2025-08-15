package com.example.auth_service.api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    protected Boolean success;
    protected String message;
    protected Object data;
    protected Object errors;

    public static ResponseEntity<ApiResponse> ok(Object data) {
        return ResponseEntity.ok(new ApiResponse(true, "", data, null));
    }

    public static ResponseEntity<ApiResponse> created(Object data) {
        return new ResponseEntity<>(new ApiResponse(true, "", data, null), HttpStatus.CREATED);
    }

    public static ResponseEntity<ApiResponse> noContent() {
        return new ResponseEntity<>(new ApiResponse(true, "", null, null), HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity<ApiResponse> unAuthorized(String message) {
        return new ResponseEntity<>(new ApiResponse(false, message, null, null), HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<ApiResponse> notFound(String message) {
        return new ResponseEntity<>(new ApiResponse(false, message, null, null), HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<ApiResponse> badRequest(Object errors) {
        return new ResponseEntity<>(new ApiResponse(false, null, null, errors), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ApiResponse> internalServerError(String message) {
        return new ResponseEntity<>(new ApiResponse(false, message, null, null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

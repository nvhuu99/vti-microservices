package com.example.api_gateway.services.auth_service.exceptions;

import com.example.api_gateway.services.auth_service.responses.BaseApiResponse;

public class InvalidTokenException extends ApiResponseException {
    public InvalidTokenException(BaseApiResponse response) {
        super(response);
    }
}
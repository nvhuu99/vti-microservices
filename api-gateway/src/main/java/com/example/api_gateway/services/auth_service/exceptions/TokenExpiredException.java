package com.example.api_gateway.services.auth_service.exceptions;

import com.example.api_gateway.services.auth_service.responses.BaseApiResponse;

public class TokenExpiredException extends ApiResponseException {
    public TokenExpiredException(BaseApiResponse response) {
        super(response);
    }
}
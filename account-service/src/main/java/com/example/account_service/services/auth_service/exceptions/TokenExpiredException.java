package com.example.account_service.services.auth_service.exceptions;

import com.example.account_service.services.auth_service.responses.BaseApiResponse;

public class TokenExpiredException extends ApiResponseException {
    public TokenExpiredException(BaseApiResponse response) {
        super(response);
    }
}
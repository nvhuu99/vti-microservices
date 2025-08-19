package com.example.account_service.services.auth_service.exceptions;

import com.example.account_service.services.auth_service.responses.BaseApiResponse;

public class InvalidTokenException extends ApiResponseException {
    public InvalidTokenException(BaseApiResponse response) {
        super(response);
    }
}
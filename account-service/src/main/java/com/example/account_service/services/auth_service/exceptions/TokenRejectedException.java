package com.example.account_service.services.auth_service.exceptions;

import com.example.account_service.services.auth_service.responses.BaseApiResponse;

public class TokenRejectedException extends ApiResponseException {
    public TokenRejectedException(BaseApiResponse response) {
        super(response);
    }
}
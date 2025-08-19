package com.example.account_service.services.auth_service.exceptions;

import com.example.account_service.services.auth_service.responses.BaseApiResponse;

public class BadCredentialsException extends ApiResponseException {

    public BadCredentialsException(BaseApiResponse response) {
        super(response);
    }
}

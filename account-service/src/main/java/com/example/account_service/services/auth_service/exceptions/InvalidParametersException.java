package com.example.account_service.services.auth_service.exceptions;

import com.example.account_service.services.auth_service.responses.BaseApiResponse;

public class InvalidParametersException extends ApiResponseException {
    public InvalidParametersException(BaseApiResponse response) {
        super(response);
    }
}

package com.example.api_gateway.services.auth_service.exceptions;

import com.example.api_gateway.services.auth_service.responses.BaseApiResponse;

public class InvalidParametersException extends ApiResponseException {
    public InvalidParametersException(BaseApiResponse response) {
        super(response);
    }
}

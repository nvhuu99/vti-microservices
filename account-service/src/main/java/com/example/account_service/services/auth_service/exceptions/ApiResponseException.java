package com.example.account_service.services.auth_service.exceptions;

import com.example.account_service.services.auth_service.responses.BaseApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lombok.Getter;

import java.nio.charset.StandardCharsets;

@Getter
public class ApiResponseException extends RuntimeException {

    private final BaseApiResponse response;

    public ApiResponseException(BaseApiResponse response) {
        super(response.getMessage());
        this.response = response;
    }

    public static ApiResponseException mapException(Response response) throws Exception {
        var body = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        var apiResponse = BaseApiResponse.fromJson(body);
        if (response.status() == 400 && !apiResponse.getMessage().isEmpty()) {
            if (apiResponse.getMessage().equals("Bad credentials")) {
                return new BadCredentialsException(apiResponse);
            }
            return new InvalidParametersException(apiResponse);
        }
        return new ApiResponseException(apiResponse);
    }
}

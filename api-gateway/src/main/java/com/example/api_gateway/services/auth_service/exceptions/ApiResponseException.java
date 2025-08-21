package com.example.api_gateway.services.auth_service.exceptions;

import com.example.api_gateway.services.auth_service.responses.BaseApiResponse;
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
        return switch (response.status()) {
            case 400 -> new InvalidTokenException(apiResponse);
            case 401 -> new TokenRejectedException(apiResponse);
            case 419 -> new TokenExpiredException(apiResponse);
            default -> new ApiResponseException(apiResponse);
        };
    }
}

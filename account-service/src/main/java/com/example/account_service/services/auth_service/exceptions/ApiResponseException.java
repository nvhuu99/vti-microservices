package com.example.account_service.services.auth_service.exceptions;

import com.example.account_service.services.auth_service.responses.BaseApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lombok.Getter;

@Getter
public class ApiResponseException extends RuntimeException {

    private final BaseApiResponse response;

    public ApiResponseException(BaseApiResponse response) {
        super(response.getMessage());
        this.response = response;
    }

    public static ApiResponseException mapException(Response response) throws Exception {
        var res = new ObjectMapper().readValue(response.body().asInputStream(), BaseApiResponse.class);

        if (response.status() == 401) {
            return new TokenRejectedException(res);
        }

        if (response.status() == 400 && !res.getMessage().isEmpty()) {
            return switch (res.getMessage()) {
                case "Bad credentials" -> new BadCredentialsException(res);
                case "Invalid token" -> new TokenExpiredException(res);
                case "Token expired" -> new TokenRejectedException(res);
                default -> new InvalidParametersException(res);
            };
        }

        return new ApiResponseException(res);
    }
}

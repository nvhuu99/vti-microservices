package com.example.account_service.services.auth_service;

import com.example.account_service.services.auth_service.exceptions.*;
import com.example.account_service.services.auth_service.responses.BaseApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class AuthServiceClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return (String methodKey, Response response) -> {
            try {
                return ApiResponseException.mapException(response);
            } catch (Exception parseException) {
                return new ErrorDecoder.Default().decode(methodKey, response);
            }
        };
    }
}


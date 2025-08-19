package com.example.auth_service.api.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class AuthenticateSuccessResponse extends ApiResponse {

    public record AuthData(String accessToken, String refreshToken) {}

    public AuthenticateSuccessResponse(String accessToken, String refreshToken) {
        super(200, true, "", new AuthData(accessToken, refreshToken), null);
    }
}

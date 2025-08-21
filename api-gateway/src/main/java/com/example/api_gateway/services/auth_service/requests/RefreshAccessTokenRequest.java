package com.example.api_gateway.services.auth_service.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshAccessTokenRequest {
    private String accessToken;
    private String refreshToken;
}
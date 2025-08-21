package com.example.api_gateway.services.auth_service.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyAccessTokenRequest {
    private String accessToken;
}

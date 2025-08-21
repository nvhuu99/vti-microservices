package com.example.api_gateway.services.auth_service;

import com.example.api_gateway.services.auth_service.requests.RefreshAccessTokenRequest;
import com.example.api_gateway.services.auth_service.requests.VerifyAccessTokenRequest;
import com.example.api_gateway.services.auth_service.responses.RefreshAccessTokenResponse;
import com.example.api_gateway.services.auth_service.responses.VerifyAccessTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "authClient",
    url = "${auth-service.url}",
    configuration = AuthServiceClientConfig.class
)
public interface AuthServiceClient {

    @PostMapping("/api/v1/auth/verify")
    VerifyAccessTokenResponse verify(@RequestBody VerifyAccessTokenRequest body);

    @PostMapping("/api/v1/auth/refresh")
    RefreshAccessTokenResponse refresh(@RequestBody RefreshAccessTokenRequest body);
}

package com.example.account_service.services.auth_service;

import com.example.account_service.services.auth_service.responses.*;
import com.example.account_service.services.auth_service.requests.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "authClient",
    url = "${auth-service.url}",
    configuration = AuthServiceClientConfig.class
)
public interface AuthServiceClient {

    @PostMapping("/api/v1/auth/authenticate")
    BasicAuthenticateResponse authenticate(@RequestBody BasicAuthenticateRequest body);

    @PostMapping("/api/v1/auth/register")
    RegisterUserResponse register(@RequestBody RegisterUserRequest body);

    @PostMapping("/api/v1/auth/verify")
    VerifyAccessTokenResponse verify(@RequestBody VerifyAccessTokenRequest body);


    @PostMapping("/api/v1/auth/refresh")
    RefreshAccessTokenResponse refresh(@RequestBody RefreshAccessTokenRequest body);
}

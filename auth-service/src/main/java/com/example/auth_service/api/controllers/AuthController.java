package com.example.auth_service.api.controllers;

import com.example.auth_service.api.requests.LoginRequest;
import com.example.auth_service.api.responses.ApiResponse;
import com.example.auth_service.services.TokenService;
import com.example.auth_service.services.UserService;
import com.example.auth_service.services.dto.SaveUser;
import com.example.auth_service.services.exceptions.BadCredentialsException;
import com.example.auth_service.services.exceptions.UsernameDuplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> loginSubmit(
        @Validated @RequestBody LoginRequest body
    ) throws Exception {
        try {
            var user = userService.login(body.getUsername(), body.getPassword());
            return ApiResponse.ok(Map.of(
                "access_token", tokenService.createAccessToken(user),
                "refresh_token", tokenService.createRefreshToken(user)
            ));
        } catch (BadCredentialsException ex) {
            return ApiResponse.unAuthorized("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated  @RequestBody SaveUser body) {
        try {
            var user = userService.register(body);
            return ResponseEntity.ok(user);
        } catch (UsernameDuplicationException exception) {
            return ApiResponse.badRequest(Map.of("username", exception.getMessage()));
        }
    }
}

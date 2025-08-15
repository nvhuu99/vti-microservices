package com.example.auth_service.api.controllers;

import com.example.auth_service.api.responses.ApiResponse;
import com.example.auth_service.api.responses.AuthenticateSuccessResponse;
import com.example.auth_service.services.dto.token_service.RefreshAccessToken;
import com.example.auth_service.services.dto.token_service.VerifyAccessToken;
import com.example.auth_service.services.dto.user_service.AuthenticateUser;
import com.example.auth_service.services.TokenService;
import com.example.auth_service.services.UserService;
import com.example.auth_service.services.dto.user_service.RegisterUser;
import com.example.auth_service.services.exceptions.token_service.TokenExpiredException;
import com.example.auth_service.services.exceptions.token_service.TokenRejectedException;
import com.example.auth_service.services.exceptions.user_service.BadCredentialsException;
import com.example.auth_service.services.exceptions.user_service.UsernameDuplicationException;
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
    public ResponseEntity<?> login(@Validated @RequestBody AuthenticateUser body) {
        try {
            var user = userService.authenticate(body.getUsername(), body.getPassword());
            return ResponseEntity.ok(new AuthenticateSuccessResponse(
                tokenService.createAccessToken(user),
                tokenService.createRefreshToken(user)
            ));
        } catch (BadCredentialsException ex) {
            return ApiResponse.unAuthorized("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterUser body) {
        try {
            var user = userService.register(body);
            return ResponseEntity.ok(user);
        } catch (UsernameDuplicationException exception) {
            return ApiResponse.badRequest(Map.of("username", exception.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@Validated @RequestBody VerifyAccessToken body) {
        try {
            tokenService.verifyAccessToken(body.getAccessToken());
            return ApiResponse.ok(null);
        } catch (TokenExpiredException ex) {
            return ApiResponse.unAuthorized("Expired");
        } catch (TokenRejectedException ex) {
            return ApiResponse.unAuthorized("Rejected");
        } catch (Exception ex) {
            return ApiResponse.unAuthorized("Invalid token");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@Validated @RequestBody RefreshAccessToken body) {
        try {
            var payload = tokenService.verifyRefreshToken(
                body.getAccessToken(),
                body.getRefreshToken()
            );
            return ApiResponse.ok(Map.of(
                "access_token", tokenService.createAccessToken(
                    userService.findByUsername(payload.getSubject())
                )
            ));
        } catch (TokenExpiredException ex) {
            return ApiResponse.unAuthorized("Expired");
        } catch (TokenRejectedException ex) {
            return ApiResponse.unAuthorized("Rejected");
        } catch (Exception ex) {
            return ApiResponse.unAuthorized("Invalid token");
        }
    }
}

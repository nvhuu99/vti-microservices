package com.example.auth_service.api.controllers;

import com.example.auth_service.api.responses.ApiResponse;
import com.example.auth_service.api.responses.AuthenticateSuccessResponse;
import com.example.auth_service.services.token_service.dto.RefreshAccessToken;
import com.example.auth_service.services.token_service.dto.VerifyAccessToken;
import com.example.auth_service.services.user_service.dto.AuthenticateUser;
import com.example.auth_service.services.token_service.TokenService;
import com.example.auth_service.services.user_service.UserService;
import com.example.auth_service.services.user_service.dto.RegisterUser;
import com.example.auth_service.services.token_service.exceptions.TokenExpiredException;
import com.example.auth_service.services.token_service.exceptions.TokenRejectedException;
import com.example.auth_service.services.user_service.user_service.BadCredentialsException;
import com.example.auth_service.services.user_service.user_service.UsernameDuplicationException;
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

    @PostMapping("/basic-authenticate")
    public ResponseEntity<?> login(@Validated @RequestBody AuthenticateUser body) {
        try {
            var user = userService.authenticate(body.getUsername(), body.getPassword());
            return ResponseEntity.ok(new AuthenticateSuccessResponse(
                tokenService.createAccessToken(user),
                tokenService.createRefreshToken(user)
            ));
        } catch (BadCredentialsException ex) {
            return ApiResponse.badRequest("Bad credentials", null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterUser body) {
        try {
            var user = userService.register(body);
            return ResponseEntity.ok(user);
        } catch (UsernameDuplicationException exception) {
            return ApiResponse.badRequest("Invalid arguments", Map.of("username", exception.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@Validated @RequestBody VerifyAccessToken body) {
        try {
            tokenService.verifyAccessToken(body.getAccessToken());
            return ApiResponse.ok(null);
        } catch (TokenRejectedException ex) {
            return ApiResponse.unAuthorized();
        } catch (TokenExpiredException ex) {
            return ApiResponse.badRequest("Token expired", null);
        }  catch (Exception ex) {
            return ApiResponse.badRequest("Invalid token", null);
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
        } catch (TokenRejectedException ex) {
            return ApiResponse.unAuthorized();
        } catch (TokenExpiredException ex) {
            return ApiResponse.badRequest("Token expired", null);
        }  catch (Exception ex) {
            return ApiResponse.badRequest("Invalid token", null);
        }
    }
}

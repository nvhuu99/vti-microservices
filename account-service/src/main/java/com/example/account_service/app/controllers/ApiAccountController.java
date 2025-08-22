package com.example.account_service.app.controllers;

import com.example.account_service.app.responses.ApiResponse;
import com.example.account_service.services.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/accounts")
public class ApiAccountController {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<?> accountDetail(@PathVariable Long id) {
        var user = userService.findById(id);
        if (user == null) {
            return ApiResponse.notFound("User not found - UserID: " + id);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("email", user.getEmail());
        data.put("profileImageUrl", user.getProfileImageUrl());
        data.put("roles", user.getRoles());
        return ApiResponse.ok(data);
    }
}

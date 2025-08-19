package com.example.auth_service.services.token_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshAccessToken {
    @NotNull(message = "Access token is required")
    String accessToken;

    @NotNull(message = "Refresh token is required")
    String refreshToken;
}

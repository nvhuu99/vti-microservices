package com.example.account_service.services.user_service.dto;

import com.example.account_service.models.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetail {
    Long id;
    String username;
    String email;

    public static UserDetail fromEntity(User entity) {
        return new UserDetail(
            entity.getId(),
            entity.getUsername(),
            entity.getEmail()
        );
    }
}
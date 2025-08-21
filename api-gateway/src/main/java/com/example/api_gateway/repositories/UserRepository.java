package com.example.api_gateway.repositories;

import com.example.api_gateway.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccessToken(String accessToken);
    Optional<User> findByUsername(String username);
}
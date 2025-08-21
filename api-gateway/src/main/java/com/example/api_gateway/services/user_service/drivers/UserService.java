package com.example.api_gateway.services.user_service.drivers;

import com.example.api_gateway.models.User;
import com.example.api_gateway.repositories.UserRepository;
import com.example.api_gateway.services.user_service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService implements com.example.api_gateway.services.user_service.UserService {

    @Autowired
    private UserRepository repo;

    @Override
    public User findByAccessToken(String accessToken) {
        return repo.findByAccessToken(accessToken).orElse(null);
    }

    @Override
    public void setAccessToken(String username, String accessToken) throws NotFoundException {
        var user = repo.findByUsername(username);
        if (user.isEmpty()) {
            throw new NotFoundException("Username", username);
        }
        user.get().setAccessToken(accessToken);
        repo.save(user.get());
    }

    @Override
    public void unsetAuthTokens(String username) {
        var user = repo.findByUsername(username);
        if (user.isPresent()) {
            user.get().setAccessToken(null);
            user.get().setRefreshToken(null);
            repo.save(user.get());
        }
    }
}

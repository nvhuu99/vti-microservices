package com.example.account_service.services.user_service.drivers;

import com.example.account_service.repositories.UserRepository;
import com.example.account_service.services.user_service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService implements com.example.account_service.services.user_service.UserService {
    @Autowired
    private UserRepository repo;

    @Override
    public void setAuthTokens(String username, String accessToken, String refreshToken) throws NotFoundException {
        var user = repo.findByUsername(username);
        if (user.isEmpty()) {
            throw new NotFoundException("Username", username);
        }
        user.get().setAccessToken(accessToken);
        user.get().setRefreshToken(refreshToken);
        repo.save(user.get());
    }

    @Override
    public void unsetAuthTokens(String accessToken) {
        var user = repo.findByAccessToken(accessToken);
        if (user.isPresent()) {
            user.get().setAccessToken(null);
            user.get().setRefreshToken(null);
            repo.save(user.get());
        }
    }
}

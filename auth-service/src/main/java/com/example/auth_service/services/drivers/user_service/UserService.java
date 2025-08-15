package com.example.auth_service.services.drivers.user_service;

import com.example.auth_service.models.User;
import com.example.auth_service.services.drivers.user_details_service.AuthenticatedUser;
import com.example.auth_service.repositories.UserRepository;
import com.example.auth_service.services.dto.user_service.RegisterUser;
import com.example.auth_service.services.exceptions.user_service.BadCredentialsException;
import com.example.auth_service.services.exceptions.user_service.UsernameDuplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements com.example.auth_service.services.UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    @Override
    public User authenticate(String username, String password) throws BadCredentialsException {
        try {
            var result = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );
            var authenticated = (AuthenticatedUser)result.getPrincipal();
            return authenticated.getUser();
        } catch (Exception ignored) {
            throw new BadCredentialsException();
        }
    }

    @Override
    public User register(RegisterUser data) throws UsernameDuplicationException {
        if (userRepo.existsByUsername(data.getUsername())) {
            throw new UsernameDuplicationException();
        }

        var user = new User();
        user.setUsername(data.getUsername());
        user.setEmail(data.getEmail());
        user.setPassword(passwordEncoder.encode(data.getPassword()));

        return userRepo.save(user);
    }
}

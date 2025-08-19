package com.example.auth_service.services.user_service.drivers;

import com.example.auth_service.models.User;
import com.example.auth_service.services.user_details_service.AuthenticatedUser;
import com.example.auth_service.repositories.UserRepository;
import com.example.auth_service.services.user_service.dto.RegisterUser;
import com.example.auth_service.services.user_service.exceptions.BadCredentialsException;
import com.example.auth_service.services.user_service.exceptions.EmailDuplicationException;
import com.example.auth_service.services.user_service.exceptions.UsernameDuplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements com.example.auth_service.services.user_service.UserService {

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
    public User register(RegisterUser data) throws UsernameDuplicationException, EmailDuplicationException {
        if (userRepo.existsByUsername(data.getUsername())) {
            throw new UsernameDuplicationException();
        }
        if (userRepo.existsByEmail(data.getEmail())) {
            throw new EmailDuplicationException();
        }

        var user = new User();
        user.setUsername(data.getUsername());
        user.setEmail(data.getEmail());
        user.setPassword(passwordEncoder.encode(data.getPassword()));

        return userRepo.save(user);
    }
}

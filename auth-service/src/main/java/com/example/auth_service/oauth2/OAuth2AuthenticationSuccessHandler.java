package com.example.auth_service.oauth2;

import com.example.auth_service.api.responses.AuthenticateSuccessResponse;
import com.example.auth_service.models.User;
import com.example.auth_service.oauth2.user_info.OAuth2UserInfoFactory;
import com.example.auth_service.repositories.UserRepository;
import com.example.auth_service.services.token_service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.*;
import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private OAuth2UserInfoFactory userInfoFactory;

    @Autowired
    private UserRepository userRepo;

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException {
        var userInfo = userInfoFactory.create(authentication);
        User user = userRepo.findByEmail(userInfo.getEmail()).orElseGet(() -> {
            var newUser = new User();
            newUser.setEmail(userInfo.getEmail());
            newUser.setUsername(userInfo.getEmail());
            newUser.getRoles().add("ROLE_USER");
            return userRepo.save(newUser);
        });

        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(
            new AuthenticateSuccessResponse(
                tokenService.createAccessToken(user),
                tokenService.createRefreshToken(user)
            ).asJson()
        );
    }
}

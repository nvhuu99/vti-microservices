package com.example.auth_service.oauth2;

import com.example.auth_service.api.responses.AuthenticateSuccessResponse;
import com.example.auth_service.models.User;
import com.example.auth_service.oauth2.auth_request_state.OAuth2AuthRequestState;
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

        var state = OAuth2AuthRequestState.fromJson(request.getParameter("state"));
        state.set("username", user.getUsername());
        state.set("accessToken", tokenService.createAccessToken(user));
        state.set("refreshToken", tokenService.createRefreshToken(user));

        var authorizedRedirect = state.get("authorizedRedirect");
        authorizedRedirect += "?" + state.toURLQueries();

        response.sendRedirect(authorizedRedirect);
    }
}

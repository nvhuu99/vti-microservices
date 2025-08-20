package com.example.account_service.app.controllers;

import com.example.account_service.app.exceptions.UnhandledException;
import com.example.account_service.services.user_service.UserService;
import com.example.account_service.services.user_service.exceptions.NotFoundException;
import com.example.account_service.utils.CookieUtil;
import com.example.account_service.utils.UrlBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
public class OAuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UrlBuilder urlBuilder;

    @Autowired
    private CookieUtil cookieUtil;

    @Value("${auth-service.url}")
    private String authServiceUrl;

    @Value("${api-gateway.url}/${api-gateway.top-page}")
    private String topPageUrl;

    @GetMapping("/oauth2/authorization/{registrationId}")
    public String authorization(
        @PathVariable String registrationId,
        @RequestParam String redirect
    ) throws UnhandledException {
        var authServiceOauth2Endpoint = urlBuilder.buildAbs(
            authServiceUrl, "oauth2/authorization/" + registrationId,
            Map.of(
                "authorizedRedirect", urlBuilder.build("oauth2/authorized", null),
                "redirect", redirect
            )
        );
        return "redirect:" + authServiceOauth2Endpoint;
    }

    @GetMapping("/oauth2/authorized")
    public String authorized(
        @RequestParam String username,
        @RequestParam String accessToken,
        @RequestParam String refreshToken,
        @RequestParam String redirect,
        HttpServletResponse response
    ) throws NotFoundException {
        userService.setAuthTokens(username, accessToken, refreshToken);
        cookieUtil.addCookie(response, "accessToken", accessToken);
        return "redirect:" + redirect;
    }
}

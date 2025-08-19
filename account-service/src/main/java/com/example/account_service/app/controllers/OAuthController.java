package com.example.account_service.app.controllers;

import com.example.account_service.services.auth_service.AuthServiceClient;
import com.example.account_service.services.user_service.UserService;
import com.example.account_service.utils.CookieUtil;
import com.example.account_service.utils.UrlBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Controller
public class OAuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UrlBuilder urlBuilder;

    @Value("${auth-service.url}")
    private String authServiceUrl;

    @GetMapping("/oauth2/authorization/{registrationId}")
    public String authorization(
        HttpServletRequest request,
        @PathVariable String registrationId,
        @RequestParam String redirect
    ) {
        if (registrationId.isEmpty()) {
            return "redirect:/error";
        }

        var authServiceOauth2Endpoint = urlBuilder.buildAbs(authServiceUrl, "oauth2/authorization/" + registrationId, Map.of(
            "authorizedRedirect", urlBuilder.build("oauth2/authorized", null),
            "redirect", urlBuilder.build(redirect, null)
        ));

        return "redirect:" + authServiceOauth2Endpoint;
    }

    @GetMapping("/oauth2/authorized")
    public String authorized(
        @RequestParam String username,
        @RequestParam String accessToken,
        @RequestParam String refreshToken,
        @RequestParam String redirect,
        HttpServletResponse response
    ) throws Exception {
        if (accessToken.isEmpty() || refreshToken.isEmpty()) {
            return "redirect:/error";
        }

        userService.setAuthTokens(username, accessToken, refreshToken);

        CookieUtil.addCookie(response, "accessToken", accessToken);

        return "redirect:" + redirect;
    }
}

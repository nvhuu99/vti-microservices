package com.example.account_service.app.controllers;

import com.example.account_service.app.exceptions.UnhandledException;
import com.example.account_service.services.auth_service.AuthServiceClient;
import com.example.account_service.services.auth_service.exceptions.BadCredentialsException;
import com.example.account_service.services.auth_service.exceptions.InvalidParametersException;
import com.example.account_service.services.auth_service.requests.BasicAuthRequest;
import com.example.account_service.services.auth_service.requests.RegisterUserRequest;
import com.example.account_service.services.user_service.UserService;
import com.example.account_service.utils.CookieUtil;
import com.example.account_service.utils.UrlBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthServiceClient authServiceAPI;

    @Autowired
    private UrlBuilder urlBuilder;

    @Autowired
    private CookieUtil cookie;

    @Value("${api-gateway.url}/${api-gateway.top-page}")
    private String topPageUrl;

    @GetMapping("register")
    public String showRegister(
        @RequestParam(required = false) String redirect,
        Model model
    ) {
        model.addAttribute("user", new RegisterUserRequest());
        model.addAttribute("redirect", redirect.isEmpty() ? topPageUrl : redirect);
        return "register";
    }

    @PostMapping("register")
    public String register(
        @RequestParam(required = false) String redirect,
        @ModelAttribute("user") RegisterUserRequest body,
        BindingResult bind
    ) throws UnhandledException {
        try {
            authServiceAPI.register(body);
            return "redirect:" + urlBuilder.build("login", Map.of(
                "redirect", redirect.isEmpty() ? topPageUrl : redirect)
            );
        } catch (InvalidParametersException apiErr) {
            apiErr.getResponse().getErrors().forEach((f, m) -> bind.rejectValue(f, "", m));
            return "register";
        } catch (Exception exception) {
            throw new UnhandledException(HttpStatus.INTERNAL_SERVER_ERROR, "Registration failure");
        }
    }

    @GetMapping("login")
    public String showLogin(
        @RequestParam(required = false) String redirect,
        Model model
    ) {
        model.addAttribute("user", new BasicAuthRequest());
        model.addAttribute("redirect", redirect.isEmpty() ? topPageUrl : redirect);
        return "login";
    }

    @PostMapping("login")
    public String login(
        @RequestParam(required = false) String redirect,
        @ModelAttribute("user") BasicAuthRequest body,
        BindingResult bind,
        HttpServletResponse response
    ) throws UnhandledException {
        try {
            var apiResponse = authServiceAPI.authenticate(body);
            var accessToken = apiResponse.getAccessToken();
            var refreshToken = apiResponse.getAccessToken();
            userService.setAuthTokens(body.getUsername(), accessToken, refreshToken);
            cookie.addCookie(response, "accessToken", apiResponse.getAccessToken());
            return "redirect:" + (redirect.isEmpty() ? topPageUrl : redirect);
        } catch (BadCredentialsException apiErr) {
            bind.reject("Incorrect username and password");
            return "login";
        } catch (InvalidParametersException apiErr) {
            apiErr.getResponse().getErrors().forEach((f, m) -> bind.rejectValue(f, "", m));
            return "login";
        } catch (Exception exception) {
            throw new UnhandledException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to login");
        }
    }

    @GetMapping("home")
    public ResponseEntity<?> home(HttpServletRequest request) {
        return ResponseEntity.ok(cookie.getCookie(request, "accessToken"));
    }

    @PostMapping("logout")
    public String logout(HttpServletRequest request) throws UnhandledException {
        try {
            var accessToken = cookie.getCookie(request, "accessToken");
            userService.unsetAuthTokens(accessToken.toString());
            return "redirect:" + urlBuilder.build("login", Map.of("redirect", topPageUrl));
        } catch (Exception exception) {
            throw new UnhandledException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to logout");
        }
    }
}

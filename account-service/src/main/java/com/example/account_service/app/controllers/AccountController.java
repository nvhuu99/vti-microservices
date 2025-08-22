package com.example.account_service.app.controllers;

import com.example.account_service.app.exceptions.UnhandledException;
import com.example.account_service.app.view_models.LoginFormAttributes;
import com.example.account_service.app.view_models.RegistrationFormAttributes;
import com.example.account_service.app.view_models.ViewModelBuilder;
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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthServiceClient authServiceAPI;

    @Autowired
    private UrlBuilder urlBuilder;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private ViewModelBuilder viewBuilder;

    @Value("${api-gateway.url}/${api-gateway.top-page}")
    private String topPageUrl;

    @GetMapping("register")
    public String showRegister(
        @RequestParam(required = false, defaultValue = "") String redirect,
        Model model
    ) {
        model.addAttribute("errors", new HashMap<>());
        model.addAttribute("user", new RegisterUserRequest());
        model.addAttribute("form", viewBuilder.buildRegistrationForm(redirect.isEmpty()
            ? topPageUrl
            : redirect
        ));
        return "register";
    }

    @PostMapping("register")
    public String register(
        @ModelAttribute("form") RegistrationFormAttributes formAttrs,
        @Valid @ModelAttribute("user") RegisterUserRequest body,
        Model model
    ) throws UnhandledException {
        try {
            authServiceAPI.register(body);
            return "redirect:" + formAttrs.loginUrl();
        } catch (InvalidParametersException apiErr) {
            model.addAttribute("errors", apiErr.getResponse().getErrors());
            return "register";
        } catch (Exception exception) {
            throw new UnhandledException(HttpStatus.INTERNAL_SERVER_ERROR, "Registration failure");
        }
    }

    @GetMapping("login")
    public String showLogin(
        @RequestParam(required = false, defaultValue = "") String redirect,
        Model model
    ) {
        model.addAttribute("errors", new HashMap<>());
        model.addAttribute("user", new BasicAuthRequest());
        model.addAttribute("form", viewBuilder.buildLoginForm(redirect.isEmpty()
            ? topPageUrl
            : redirect
        ));
        return "login";
    }

    @PostMapping("login")
    public String login(
        @ModelAttribute("form") LoginFormAttributes formAttrs,
        @ModelAttribute("user") BasicAuthRequest body,
        Model model,
        HttpServletResponse response
    ) throws UnhandledException {
        try {
            var apiResponse = authServiceAPI.authenticate(body);
            var accessToken = apiResponse.getAccessToken();
            var refreshToken = apiResponse.getRefreshToken();
            userService.setAuthTokens(body.getUsername(), accessToken, refreshToken);
            cookieUtil.set(response, "accessToken", accessToken);
            return "redirect:" + formAttrs.redirect();
        } catch (BadCredentialsException apiErr) {
            model.addAttribute("errors", Map.of("global", new String[]{ "Incorrect username and password" }));
            return "login";
        } catch (InvalidParametersException apiErr) {
            model.addAttribute("errors", apiErr.getResponse().getErrors());
            return "login";
        } catch (Exception exception) {
            throw new UnhandledException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to login");
        }
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) throws UnhandledException {
        try {
            var accessToken = cookieUtil.get(request, "accessToken");
            if (accessToken.isEmpty()) {
                var header = request.getHeader("Authorization");
                if (header != null && header.startsWith("Bearer ")) {
                    accessToken = header.substring(7);
                }
            }
            userService.unsetAuthTokens(accessToken);
            return "redirect:" + urlBuilder.build("account/login", Map.of("redirect", topPageUrl));
        } catch (Exception exception) {
            throw new UnhandledException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to logout");
        }
    }
}

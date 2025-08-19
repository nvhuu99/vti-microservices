package com.example.account_service.app.controllers;

import com.example.account_service.services.auth_service.AuthServiceClient;
import com.example.account_service.services.auth_service.exceptions.ApiResponseException;
import com.example.account_service.services.auth_service.exceptions.BadCredentialsException;
import com.example.account_service.services.auth_service.exceptions.InvalidParametersException;
import com.example.account_service.services.auth_service.requests.BasicAuthenticateRequest;
import com.example.account_service.services.auth_service.requests.RegisterUserRequest;
import com.example.account_service.services.auth_service.responses.BasicAuthenticateResponse;
import com.example.account_service.services.user_service.UserService;
import com.example.account_service.utils.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthServiceClient authServiceClient;

    @GetMapping("register")
    public String showRegister(Model model) {
        model.addAttribute("user", new RegisterUserRequest());
        return "register";
    }

    @PostMapping("register")
    public String register(
            @ModelAttribute("user") RegisterUserRequest body,
            BindingResult bindingResult
    ) {
        try {
            authServiceClient.register(body);
            return "redirect:/login";
        } catch (ApiResponseException apiErr) {
            if (apiErr instanceof InvalidParametersException) {
                apiErr.getResponse().getErrors()
                    .forEach((field, msg) -> bindingResult.rejectValue(field, "", msg));
            } else {
                return "redirect:/error";
            }
            return "register";
        }
    }

    @GetMapping("login")
    public String showLogin(Model model) {
        model.addAttribute("user", new BasicAuthenticateRequest());
        return "login";
    }

    @PostMapping("login")
    public String login(
            @ModelAttribute("user") BasicAuthenticateRequest body,
            BindingResult bindingResult,
            HttpServletResponse res
    ) throws Exception {
        try {
            BasicAuthenticateResponse response = authServiceClient.authenticate(body);

            userService.setAuthTokens(
                body.getUsername(),
                response.getAccessToken(),
                response.getRefreshToken()
            );

            CookieUtil.addCookie(res, "accessToken", response.getAccessToken());

            return "redirect:home";

        } catch (ApiResponseException apiErr) {
            if (apiErr instanceof BadCredentialsException) {
                bindingResult.reject(apiErr.getMessage());
            } else if (apiErr instanceof InvalidParametersException) {
                apiErr.getResponse().getErrors()
                    .forEach((field, msg) -> bindingResult.rejectValue(field, "", msg));
            } else {
                return "redirect:/error";
            }
            return "login";
        }
    }

    @GetMapping("home")
    public ResponseEntity<?> home(HttpServletRequest request) {
        return ResponseEntity.ok(CookieUtil.getCookie(request, "accessToken"));
    }

//
//    @GetMapping("logout")
//    public String logout() throws Exception {
//        if (isLoggedIn()) {
//            var auth = SecurityContextHolder.getContext().getAuthentication();
//            var usrDetail = (AuthUser)auth.getPrincipal();
//            usrSvc.invalidateAccessToken(usrDetail.getUsername());
//        }
//        return "redirect:/admin/login";
//    }
}


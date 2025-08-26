package com.example.account_service.app.controllers;

import com.example.account_service.services.user_service.UserService;
import com.example.account_service.utils.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private CookieUtil cookieUtil;

    @GetMapping("profile")
    public String profile(
        HttpServletRequest request,
        Model model
    ) {
        var user = userService.findByAccessToken(
            cookieUtil.get(request, "accessToken")
        );
        model.addAttribute("user", user);
        return "profile";
    }
}

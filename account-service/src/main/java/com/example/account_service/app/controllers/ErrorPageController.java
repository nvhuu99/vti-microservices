package com.example.account_service.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("account")
public class ErrorPageController {

    @GetMapping("error")
    public String error(
        @RequestParam(required = false, defaultValue = "500") Integer statusCode,
        @RequestParam(required = false, defaultValue = "") String errorMessage,
        Model model
    ) {
        var defaultMessage = switch (statusCode) {
            case 400 -> "Bad request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 404 -> "Resource not found";
            case 500 -> "Internal server error";
            default -> "Unexpected error";
        };
        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", errorMessage.isEmpty() ? defaultMessage : errorMessage);
        return "error";
    }
}

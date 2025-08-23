package com.example.auth_service.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CookieUtil {

    @Value("${api-gateway.host}")
    private String defaultDomain;

    public void set(HttpServletResponse response, String name, String value, Integer maxAgeSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setDomain(defaultDomain);
        cookie.setMaxAge(maxAgeSeconds);
        response.addCookie(cookie);
    }

    public String get(HttpServletRequest request, String name) {
        try {
            return Arrays.stream(request.getCookies())
                .filter(c -> name.equals(c.getName()))
                .findFirst()
                .get()
                .getValue();
        } catch (Exception ignored) {
            return "";
        }
    }

    public void delete(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

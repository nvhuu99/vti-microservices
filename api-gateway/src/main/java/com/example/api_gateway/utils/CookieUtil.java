package com.example.api_gateway.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.Duration;

@Component
public class CookieUtil {

    @Value("${server.cookie.max-age-seconds}")
    private Long maxAgeSeconds;

    public String get(ServerWebExchange exchange, String name) {
        try {
            return exchange.getRequest().getCookies().getFirst(name).getValue();
        } catch (Exception ignored) {
            return "";
        }
    }

    public void set(ServerWebExchange exchange, String name, String value) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
            .path("/")
            .httpOnly(true)
            .maxAge(Duration.ofSeconds(maxAgeSeconds))
            .build();
        exchange.getResponse().addCookie(cookie);
    }

    public void delete(ServerWebExchange exchange, String name) {
        ResponseCookie expired = ResponseCookie.from(name, "")
            .path("/")
            .httpOnly(true)
            .maxAge(Duration.ZERO)
            .build();
        exchange.getResponse().addCookie(expired);
    }
}

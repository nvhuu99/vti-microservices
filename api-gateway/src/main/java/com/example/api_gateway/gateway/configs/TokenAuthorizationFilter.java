package com.example.api_gateway.gateway.configs;

import com.example.api_gateway.services.auth_service.AuthServiceClient;
import com.example.api_gateway.services.auth_service.exceptions.ApiResponseException;
import com.example.api_gateway.services.auth_service.exceptions.TokenExpiredException;
import com.example.api_gateway.services.auth_service.requests.RefreshAccessTokenRequest;
import com.example.api_gateway.services.auth_service.requests.VerifyAccessTokenRequest;
import com.example.api_gateway.services.auth_service.responses.RefreshAccessTokenResponse;
import com.example.api_gateway.services.user_service.UserService;
import com.example.api_gateway.utils.CookieUtil;
import com.example.api_gateway.utils.UrlBuilder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

@Component
public class TokenAuthorizationFilter implements GatewayFilter {

    @Lazy
    @Autowired
    private AuthServiceClient authServiceApi;

    @Autowired
    private UserService userService;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private UrlBuilder urlBuilder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var accessToken = extractAccessToken(exchange, exchange.getRequest());
        if (accessToken.isEmpty()) {
            return redirectToLogin(exchange);
        }
        try {
            authServiceApi.verify(new VerifyAccessTokenRequest(accessToken));
            return chain.filter(exchange);
        } catch (TokenExpiredException ex) {
            return refreshToken(exchange, chain, accessToken);
        } catch (ApiResponseException ex) {
            return redirectToLogin(exchange);
        }
    }

    private String extractAccessToken(ServerWebExchange exchange, ServerHttpRequest request) {
        String accessToken = cookieUtil.get(exchange, "accessToken");
        if (! accessToken.isEmpty()) {
            return accessToken;
        }

        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return "";
    }

    private Mono<Void> refreshToken(ServerWebExchange exchange, GatewayFilterChain chain, String accessToken) {
        var user = userService.findByAccessToken(accessToken);
        if (user == null || user.getRefreshToken() == null) {
            return redirectToLogin(exchange);
        }
        try {
            RefreshAccessTokenResponse apiResponse = authServiceApi.refresh(new RefreshAccessTokenRequest(
                accessToken,
                user.getRefreshToken()
            ));
            userService.setAccessToken(user.getUsername(), apiResponse.getAccessToken());
            cookieUtil.set(exchange, "accessToken", apiResponse.getAccessToken());
            return chain.filter(exchange);
        } catch (Exception ex) {
            userService.unsetAuthTokens(user.getUsername());
            return redirectToLogin(exchange);
        }
    }

    private Mono<Void> redirectToLogin(ServerWebExchange exchange) {
        cookieUtil.delete(exchange, "accessToken");
        return redirectTo(exchange, urlBuilder.build("account/login", Map.of(
            "redirect", exchange.getRequest().getURI().toString())
        ));
    }

    private Mono<Void> redirectTo(ServerWebExchange exchange, String path) {
        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
        exchange.getResponse().getHeaders().setLocation(URI.create(path));
        return exchange.getResponse().setComplete();
    }
}

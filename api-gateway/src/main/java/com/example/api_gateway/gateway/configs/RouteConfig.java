package com.example.api_gateway.gateway.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;

@Configuration
public class RouteConfig {
    
    @Autowired
    TokenAuthorizationFilter tokenAuthorizationFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        var routes = builder.routes();
        /*
        Account Service Pages & APIs
        */
        routes.route("app-account", r -> r
            .path("/account/**")
            .uri("lb://account-service")
        );
        routes.route("api-account", r -> r
            .path(
                "/profile",
                "/api/v1/accounts/**"
            )
            .filters(f -> f.filter(tokenAuthorizationFilter))
            .uri("lb://account-service")
        );
        /*
         Auth Service API Endpoints
        */
        routes.route("api-auth-oauth2", r -> r
            .path(
                "/api/v1/auth/**",
                "/oauth2/**",
                "/login/oauth2/**"
            )
            .uri("lb://auth-service")
        );
        /*
        Admin Pages
        */
        routes.route("app-admin", r -> r
            .path("/admin/**")
            .filters(f -> f.filter(tokenAuthorizationFilter))
            .uri("lb://admin-service")
        );
        /*
        Department Service API Endpoints
        */
        routes.route("api-department", r -> r
            .path(
                "/api/v1/departments/**",
                "/api/v1/employees/**"
            )
            .filters(f -> f.filter(tokenAuthorizationFilter))
            .uri("lb://department-service")
        );

        return routes.build();
    }
}

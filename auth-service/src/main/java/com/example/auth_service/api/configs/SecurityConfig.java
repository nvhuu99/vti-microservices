package com.example.auth_service.api.configs;

import com.example.auth_service.oauth2.OAuth2AuthRequestCookieRepository;
import com.example.auth_service.oauth2.OAuth2HandleAuthFailure;
import com.example.auth_service.oauth2.OAuth2HandleAuthSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class SecurityConfig {

    private static final String[] WHITE_LIST = {
        "/api/v1/auth/**",
    };

    @Autowired
    private OAuth2HandleAuthSuccess oauth2SuccessHandler;

    @Autowired
    private OAuth2HandleAuthFailure oauth2FailureHandler;

    @Autowired
    private OAuth2AuthorizationRequestResolver oauth2AuthorizationRequestResolver;

    @Autowired
    OAuth2AuthRequestCookieRepository oauth2AuthRequestCookieRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(WHITE_LIST).permitAll()
                .anyRequest().denyAll()
            )
            .exceptionHandling(e -> e
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(authorization -> authorization
                    .authorizationRequestResolver(oauth2AuthorizationRequestResolver)
                    .authorizationRequestRepository(oauth2AuthRequestCookieRepository)
                )
                .successHandler(oauth2SuccessHandler)
                .failureHandler(oauth2FailureHandler)
            )
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable);

        return http.build();
    }
}

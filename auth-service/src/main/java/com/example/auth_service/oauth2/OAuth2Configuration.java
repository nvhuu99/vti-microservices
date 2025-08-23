package com.example.auth_service.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class OAuth2Configuration {
    @Bean
    public OAuth2AuthRequestCustomizer customAuthorizationRequestResolver(ClientRegistrationRepository repo) {
        return new OAuth2AuthRequestCustomizer(repo, "/oauth2/authorization");
    }
}

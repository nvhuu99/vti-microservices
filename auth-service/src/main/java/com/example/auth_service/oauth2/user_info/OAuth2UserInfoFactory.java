package com.example.auth_service.oauth2.user_info;

import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

import com.example.auth_service.oauth2.enums.OAuth2Provider;

@Component
@NoArgsConstructor
public class OAuth2UserInfoFactory {

    public OAuth2UserInfo create(String registrationId, Map<String, Object> attributes) {
        if (! registrationId.isEmpty()) {
            var provider = OAuth2Provider.fromValue(registrationId);
            return switch (provider) {
                case GOOGLE -> new GoogleOAuth2UserInfo(attributes);
                case GITHUB -> new GithubOAuth2UserInfo(attributes);
            };
        }
        return null;
    }

    public OAuth2UserInfo create(Authentication authentication) {
        String registrationId = null;
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            registrationId = oauthToken.getAuthorizedClientRegistrationId();
        }

        Map<String, Object> attributes = null;
        if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            attributes = oidcUser.getAttributes();
        } else if (authentication.getPrincipal() instanceof OAuth2User oauth2User) {
            attributes = oauth2User.getAttributes();
        }

        return create(registrationId, attributes);
    }
}

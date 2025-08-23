package com.example.auth_service.oauth2;

import com.example.auth_service.utils.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

@Component
public class OAuth2AuthRequestCookieRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public static final String AUTH_REQUEST_COOKIE_NAME = "OAUTH2_AUTH_REQUEST";

    private static final int COOKIE_EXPIRE_SECONDS = 180;

    @Autowired
    private CookieUtil cookieUtil;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        try {
            var base64 = cookieUtil.get(request, AUTH_REQUEST_COOKIE_NAME);
            return deserialize(base64);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void saveAuthorizationRequest(
        OAuth2AuthorizationRequest authorizationRequest,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        if (authorizationRequest == null) {
            cookieUtil.delete(response, AUTH_REQUEST_COOKIE_NAME);
            return;
        }
        var base64 = serialize(authorizationRequest);
        cookieUtil.set(response, AUTH_REQUEST_COOKIE_NAME, base64, COOKIE_EXPIRE_SECONDS);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        OAuth2AuthorizationRequest req = loadAuthorizationRequest(request);
        cookieUtil.delete(response, AUTH_REQUEST_COOKIE_NAME);
        return req;
    }

    private String serialize(Object object) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(object));
    }

    public OAuth2AuthorizationRequest deserialize(String base64) throws Exception {
        byte[] data = Base64.getUrlDecoder().decode(base64);
        return (OAuth2AuthorizationRequest)SerializationUtils.deserialize(data);
    }
}

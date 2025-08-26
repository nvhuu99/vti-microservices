package com.example.account_service.app.view_models;

import com.example.account_service.utils.UrlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ViewModelBuilder {

    @Autowired
    private UrlBuilder urlBuilder;

    public RegistrationFormAttributes buildRegistrationForm(String successRedirect) {
        return new RegistrationFormAttributes(
            successRedirect,
            urlBuilder.build("account/register", null),
            urlBuilder.build("account/login", Map.of("redirect", successRedirect)),
            urlBuilder.build("account/oauth2/authorization/google", Map.of("redirect", successRedirect)),
            urlBuilder.build("account/oauth2/authorization/github", Map.of("redirect", successRedirect))
        );
    }

    public LoginFormAttributes buildLoginForm(String successRedirect) {
        return new LoginFormAttributes(
            successRedirect,
            urlBuilder.build("account/login", null),
            urlBuilder.build("account/register", Map.of("redirect", successRedirect)),
            urlBuilder.build("account/oauth2/authorization/google", Map.of("redirect", successRedirect)),
            urlBuilder.build("account/oauth2/authorization/github", Map.of("redirect", successRedirect))
        );
    }
}

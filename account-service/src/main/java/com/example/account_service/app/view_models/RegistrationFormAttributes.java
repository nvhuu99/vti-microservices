package com.example.account_service.app.view_models;

public record RegistrationFormAttributes(
    String redirect,
    String registrationUrl,
    String loginUrl,
    String googleOAuth2Url,
    String githubOAuth2Url
) {}

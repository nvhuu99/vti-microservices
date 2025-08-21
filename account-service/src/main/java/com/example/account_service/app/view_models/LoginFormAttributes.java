package com.example.account_service.app.view_models;

public record LoginFormAttributes(
    String redirect,
    String loginUrl,
    String registrationUrl,
    String googleOAuth2Url,
    String githubOAuth2Url
) {}

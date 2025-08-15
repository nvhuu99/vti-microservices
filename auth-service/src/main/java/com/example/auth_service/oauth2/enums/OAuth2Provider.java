package com.example.auth_service.oauth2.enums;

public enum OAuth2Provider {
    GOOGLE("google"),
    GITHUB("github");

    private final String value;

    OAuth2Provider(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OAuth2Provider fromValue(String value) {
        for (OAuth2Provider c : values()) {
            if (c.value.equalsIgnoreCase(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}

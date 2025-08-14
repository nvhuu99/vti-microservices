package com.example.auth_service.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AuthenticatedType {
    INTERNAL("internal"),
    OAUTH2("oauth2");

    private final String value;

    AuthenticatedType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static AuthenticatedType fromValue(String value) {
        for (AuthenticatedType c : values()) {
            if (c.value.equalsIgnoreCase(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}

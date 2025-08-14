package com.example.auth_service.models.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AuthenticatedTypeConverter implements AttributeConverter<AuthenticatedType, String> {

    @Override
    public String convertToDatabaseColumn(AuthenticatedType attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public AuthenticatedType convertToEntityAttribute(String dbData) {
        return dbData != null ? AuthenticatedType.fromValue(dbData) : null;
    }
}
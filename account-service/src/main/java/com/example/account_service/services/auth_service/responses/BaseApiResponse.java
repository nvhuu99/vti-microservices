package com.example.account_service.services.auth_service.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseApiResponse {
    protected Integer status;
    protected Boolean success;
    protected String message;
    protected Object data;
    protected Map<String, String> errors = new HashMap<>();

    public Map<String, String> getErrors() {
        if (errors != null) {
            return errors;
        }
        return new HashMap<>();
    }
}

package com.example.auth_service.oauth2.auth_request_state;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@NoArgsConstructor
public class OAuth2AuthRequestState {

    private Map<String, String[]> state = new HashMap<>();

    public OAuth2AuthRequestState(HttpServletRequest request) {
        var parameters = request.getParameterMap();
        state.putAll(parameters);
    }

    public String get(String key) {
        return state.get(key)[0];
    }

    public void set(String key, String value) {
        state.put(key, new String[]{ value });
    }

    public static OAuth2AuthRequestState fromJson(String jsonData) {
        var requestState = new OAuth2AuthRequestState();
        try {
            requestState.state = new ObjectMapper().readValue(jsonData, new TypeReference<>() {});
        } catch (Exception ignored) {
        }
        return requestState;
    }

    public String toJson() {
        try {
            var json = new ObjectMapper().writeValueAsString(state);
            return json;
        } catch (Exception ignored) {
            return "";
        }
    }

    public String toURLQueries() {
        var joiner = new StringJoiner("&");
        var charset = StandardCharsets.UTF_8;
        var mapper = new ObjectMapper();
        for (var entry : state.entrySet()) {
            try {
                String value = "";
                if (entry.getValue().length == 1) {
                    value = entry.getValue()[0];
                } else {
                    value = mapper.writeValueAsString(entry.getValue());
                }
                var encodedKey = URLEncoder.encode(entry.getKey(), charset);
                var encodedValue = URLEncoder.encode(value, charset);
                joiner.add(encodedKey + "=" + encodedValue);
            } catch (Exception ignored) {
            }
        }
        return joiner.toString();
    }
}

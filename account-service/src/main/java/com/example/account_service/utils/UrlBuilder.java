package com.example.account_service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

@Component
public class UrlBuilder {

    @Value("${api-gateway.url}")
    private String baseUrl;

    private final Charset charset = StandardCharsets.UTF_8;

    public String build(String path, Map<String, Object> queries) {
        return buildAbs(baseUrl, path, queries);
    }

    public String buildAbs(String base, String path, Map<String, Object> queries) {
        var url = base + "/" + path;
        if (queries != null) {
            var joiner = new StringJoiner("&");
            var mapper = new ObjectMapper();
            for (var entry : queries.entrySet()) {
                try {
                    String value = "";
                    if (! (entry.getValue() instanceof String)) {
                        value = mapper.writeValueAsString(entry.getValue());
                    } else {
                        value = (String)entry.getValue();
                    }
                    joiner.add(encode(entry.getKey()) + "=" + encode(value));
                } catch (Exception ignored) {
                }
            }
            url += "?" + joiner;
        }
        return url;
    }

    public String encode(String value) {
        return URLEncoder.encode(value, charset);
    }
}

package com.example.account_service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

@Component
public class UrlBuilder {

    @Value("${server.host}")
    private String host;

    @Value("${server.port}")
    private String port;

    @Value("${server.scheme}")
    private String scheme;

    private final Charset charset = StandardCharsets.UTF_8;


    public String build(String path, Map<String, Object> queries) {
        String base = scheme + "://" + host + ":" + port;
        return buildAbs(base, path, queries);
    }

    public String buildAbs(String base, String path, Map<String, Object> queries) {
        var url = base + "/" + path;
        if (queries != null) {
            var joiner = new StringJoiner("&");
            var charset = StandardCharsets.UTF_8;
            var mapper = new ObjectMapper();
            for (var entry : queries.entrySet()) {
                try {
                    String value = "";
                    if (! (entry.getValue() instanceof String)) {
                        value = mapper.writeValueAsString(entry.getValue());
                    } else {
                        value = (String)entry.getValue();
                    }
                    var encodedKey = URLEncoder.encode(entry.getKey(), charset);
                    var encodedValue = URLEncoder.encode(value, charset);
                    joiner.add(encodedKey + "=" + encodedValue);
                } catch (Exception ignored) {
                }
            }
            url += "?" + joiner.toString();
        }
        return url;
    }
}

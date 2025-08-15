package com.example.auth_service.api.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
public class AuthenticateSuccessResponse extends ApiResponse {

    public static record AuthData(String accessToken, String refreshToken) {}

    public AuthenticateSuccessResponse(String accessToken, String refreshToken) {
        super(true, "", new AuthData(accessToken, refreshToken), null);
    }

    public String asJson() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }
}

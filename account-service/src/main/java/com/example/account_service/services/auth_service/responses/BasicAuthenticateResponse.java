package com.example.account_service.services.auth_service.responses;

import com.example.account_service.services.auth_service.records.BasicAuthenticateResponseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
public class BasicAuthenticateResponse extends BaseApiResponse{

    protected BasicAuthenticateResponseData data;

    public String getAccessToken() { return getData().accessToken(); }

    public String getRefreshToken() { return getData().refreshToken(); }
}


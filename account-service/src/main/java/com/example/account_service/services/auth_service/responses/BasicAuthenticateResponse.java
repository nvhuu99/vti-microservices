package com.example.account_service.services.auth_service.responses;

import com.example.account_service.services.auth_service.records.BasicAuthResponseData;
import lombok.Data;

@Data
public class BasicAuthenticateResponse extends BaseApiResponse{

    protected BasicAuthResponseData data;

    public String getAccessToken() { return getData().accessToken(); }

    public String getRefreshToken() { return getData().refreshToken(); }
}


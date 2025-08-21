package com.example.api_gateway.services.auth_service.responses;

import com.example.api_gateway.services.auth_service.records.RefreshAccessTokenResponseData;
import lombok.Data;

@Data
public class RefreshAccessTokenResponse extends BaseApiResponse {

    public RefreshAccessTokenResponseData getData() {
        if (this.data instanceof RefreshAccessTokenResponseData responseData) {
            return responseData;
        }
        return null;
    }

    public String getAccessToken() {
        if (getData() != null) {
           return getData().accessToken();
        }
        return "";
    }
}


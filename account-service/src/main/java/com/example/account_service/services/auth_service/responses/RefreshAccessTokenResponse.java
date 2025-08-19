package com.example.account_service.services.auth_service.responses;

import com.example.account_service.services.auth_service.records.RefreshAccessTokenResponseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RefreshAccessTokenResponse extends BaseApiResponse {

    public RefreshAccessTokenResponseData getData() {
        if (this.data instanceof RefreshAccessTokenResponseData responseData) {
            return responseData;
        }
        return null;
    }
}


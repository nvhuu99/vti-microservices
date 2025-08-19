package com.example.account_service.services.auth_service.exceptions;

import com.example.account_service.services.auth_service.responses.BaseApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class ApiResponseException extends RuntimeException {

    private final BaseApiResponse response;

    public ApiResponseException(BaseApiResponse response) {
        super(response.getMessage());
        this.response = response;
    }

    public BaseApiResponse getResponse() {
        return  response;
    }

    public static ApiResponseException mapException(Response response) throws Exception {
        var res = new ObjectMapper().readValue(response.body().asInputStream(), BaseApiResponse.class);

        if (response.status() == 401) {
            return new TokenRejectedException(res);
        }

        if (response.status() == 400) {
            var mssg = res.getMessage();
            mssg = mssg == null ? "" : mssg;
            switch (mssg) {
                case "Bad credentials":
                    return new BadCredentialsException(res);
                case "Invalid token":
                    return new TokenExpiredException(res);
                case "Token expired":
                    return new TokenRejectedException(res);
                default:
                    return new InvalidParametersException(res);
            }
        }

        return new ApiResponseException(res);
    }
}

package com.example.account_service.services.auth_service.responses;

import com.example.account_service.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterUserResponse extends BaseApiResponse {

    public User getData() {
        if (this.data instanceof User responseData) {
            return responseData;
        }
        return null;
    }
}


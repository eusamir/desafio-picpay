package com.example.picpay_challenger.domain.service;

import com.example.picpay_challenger.integrations.AuthClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthorizationService {
    private final AuthClient authClient;

    public boolean isAuthorized() {
        var response = authClient.authorize();
        return response != null &&
                response.getData() != null &&
                Boolean.TRUE.equals(response.getData().getAuthorization());
    }
}

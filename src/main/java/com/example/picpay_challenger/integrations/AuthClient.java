package com.example.picpay_challenger.integrations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "authorizationClient", url = "https://util.devi.tools/api/v2")
public interface AuthClient {
    @GetMapping("/authorize")
    AuthorizationResponse authorize();

    @Setter
    @Getter
    class AuthorizationResponse {
        private String status;
        private Data data;

        @Setter
        @Getter
        public static class Data {
            private Boolean authorization;

        }
    }
}

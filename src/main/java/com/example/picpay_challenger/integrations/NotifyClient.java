package com.example.picpay_challenger.integrations;

import com.example.picpay_challenger.domain.model.dto.NotifyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "authorizationClient", url = "https://util.devi.tools/api/v1/notify")
public interface NotifyClient {
    @PostMapping
    void sendNotification(NotifyDto notifyDto);
}

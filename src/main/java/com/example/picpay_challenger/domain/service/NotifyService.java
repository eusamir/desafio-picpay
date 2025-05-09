package com.example.picpay_challenger.domain.service;

import com.example.picpay_challenger.domain.model.dto.NotifyDto;
import com.example.picpay_challenger.integrations.NotifyClient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotifyService {

    private final EmailService emailService;
    private final NotifyClient notifyClient;

    public void notifyUser(String email, String message) {
        try {
            emailService.emailNotifier(email, "Notificação de Transferência", message);
        } catch (Exception e) {
            System.err.println("Falha ao enviar e-mail: " + e.getMessage());
        }

        try {
            notifyClient.sendNotification(new NotifyDto(email, message));
        } catch (Exception e) {
            System.err.println("Falha ao notificar serviço externo: " + e.getMessage());
        }
    }
}

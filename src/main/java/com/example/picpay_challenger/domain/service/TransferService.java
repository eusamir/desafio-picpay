package com.example.picpay_challenger.domain.service;

import com.example.picpay_challenger.domain.model.dto.TransferDto;
import com.example.picpay_challenger.domain.model.entity.Transfer;
import com.example.picpay_challenger.domain.model.entity.User;
import com.example.picpay_challenger.domain.model.entity.Wallet;
import com.example.picpay_challenger.domain.repository.TransferRepository;
import com.example.picpay_challenger.domain.repository.UserRepository;
import com.example.picpay_challenger.domain.repository.WalletRepository;
import com.example.picpay_challenger.mapper.TransferConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransferService {
    private TransferRepository transferRepository;
    private UserRepository userRepository;
    private WalletRepository walletRepository;
    private WalletService walletService;
    private AuthorizationService authorizationService;
    private  EmailService emailService;
    private NotifyService notifyService;

    @Transactional
    public TransferDto transfer(TransferDto transferDto, Long receiverId, Long senderId){
        if (!authorizationService.isAuthorized()) {
            throw new RuntimeException("Transferência não autorizada pelo serviço externo.");
        }
        User sender = userRepository.findById(senderId)
                .orElseThrow(()-> new RuntimeException("Remetente não encontrado."));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(()-> new RuntimeException("Destinatário não encontrado."));

        Wallet senderWallet = sender.getWallet();
        Wallet receiverWallet = receiver.getWallet();

        walletService.debit(senderId,transferDto.getAmount());
        walletService.credit(receiverId, transferDto.getAmount());

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        Transfer transfer = new Transfer(
                UUID.randomUUID(),
                Timestamp.from(Instant.now()),
                transferDto.getAmount(),
                receiver,
                sender
        );
        try {
            notifyService.notifyUser(
                    receiver.getEmail(),
                    "Você recebeu uma transferência de R$ " + transferDto.getAmount()
            );
            notifyService.notifyUser(
                    sender.getEmail(),
                    "Você enviou uma transferência de R$ " + transferDto.getAmount()
            );
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação: " + e.getMessage());
        }
        Transfer transferSaved = transferRepository.save(transfer);
        return TransferConverter.toDto(transferSaved);
    }
}

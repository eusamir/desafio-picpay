package com.example.picpay_challenger.domain.service;

import com.example.picpay_challenger.domain.enums.Message;
import com.example.picpay_challenger.domain.model.dto.TransferDto;
import com.example.picpay_challenger.domain.model.entity.Transfer;
import com.example.picpay_challenger.domain.model.entity.User;
import com.example.picpay_challenger.domain.model.entity.Wallet;
import com.example.picpay_challenger.domain.repository.TransferRepository;
import com.example.picpay_challenger.domain.repository.UserRepository;
import com.example.picpay_challenger.domain.repository.WalletRepository;
import com.example.picpay_challenger.mapper.TransferConverter;
import com.example.picpay_challenger.suport.expection.NotAuthorizedException;
import com.example.picpay_challenger.suport.expection.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransferService {
    private final TransferRepository transferRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final WalletService walletService;
    private final AuthorizationService authorizationService;
    private final UserService userService;
    private final NotifyService notifyService;

    @Transactional
    public TransferDto transfer(TransferDto transferDto, Long receiverId, Long senderId){
        if (!authorizationService.isAuthorized()) {
            throw new NotAuthorizedException(Message.TRANSFERENCIA_NAO_AUTORIZADA.getMessage());
        }
        User sender = userRepository.findById(senderId)
                .orElseThrow(()-> new NotFoundException(Message.USUARIO_NAO_ENCONTRADO.getMessage()));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(()-> new NotFoundException(Message.USUARIO_NAO_ENCONTRADO.getMessage()));

        userService.validateUserCanTransfer(sender);

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
                    "Você enviou uma transferência de R$ " + transferDto.getAmount() + " para " + receiver.getName()
            );
            notifyService.notifyUser(
                    sender.getEmail(),
                    "Você recebeu uma transferência de R$ " + transferDto.getAmount() + " de " + sender.getName()
            );
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação: " + e.getMessage());
        }
        Transfer transferSaved = transferRepository.save(transfer);
        return TransferConverter.toDto(transferSaved);
    }
}

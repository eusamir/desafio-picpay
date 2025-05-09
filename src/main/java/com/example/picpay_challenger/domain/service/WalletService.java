package com.example.picpay_challenger.domain.service;

import com.example.picpay_challenger.domain.enums.Message;
import com.example.picpay_challenger.domain.model.dto.WalletDto;
import com.example.picpay_challenger.domain.model.entity.User;
import com.example.picpay_challenger.domain.model.entity.Wallet;
import com.example.picpay_challenger.domain.repository.UserRepository;
import com.example.picpay_challenger.domain.repository.WalletRepository;
import com.example.picpay_challenger.mapper.WalletConverter;
import com.example.picpay_challenger.suport.expection.BadRequestException;
import com.example.picpay_challenger.suport.expection.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class WalletService {
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public void credit(Long id, BigDecimal amount){
        Wallet wallet = walletRepository.findByUserId(id)
                .orElseThrow(()-> new NotFoundException(Message.CARTEIRA_NAO_ENCONTRADA.getMessage()));
        BigDecimal currentBalance = wallet.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);


        wallet.setBalance(currentBalance.add(amount));
        walletRepository.save(wallet);

        logger.info("Crédito de {} adicionado à carteira do usuário com ID {}. Saldo atual: {}", amount, id, newBalance);
    }

    public void creditForTest(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(Message.USUARIO_NAO_ENCONTRADO.getMessage()));

        Wallet wallet = user.getWallet();

        BigDecimal currentBalance = wallet.getBalance() != null ? wallet.getBalance() : BigDecimal.ZERO;
        BigDecimal newBalance = currentBalance.add(amount);

        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
    }

    public void debit(Long id, BigDecimal amount){
        Wallet wallet = walletRepository.findByUserId(id)
                .orElseThrow(()-> new NotFoundException(Message.CARTEIRA_NAO_ENCONTRADA.getMessage()));
        BigDecimal currentBalance = wallet.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);

        if (currentBalance.compareTo(amount)< 0){
            logger.error("Saldo insuficiente para realizar o débito. Saldo atual: {}, valor a ser debitado: {}", currentBalance, amount);
            throw new BadRequestException(Message.SALDO_INSUFICIENTE.getMessage());
        }

        wallet.setBalance(currentBalance.subtract(amount));
        walletRepository.save(wallet);

        logger.info("Crédito de {} retirado da carteira do usuário com ID {}. Saldo atual: {}", amount, id, newBalance);
    }

    public WalletDto getBalance(Long userId){
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(()-> new NotFoundException(Message.CARTEIRA_NAO_ENCONTRADA.getMessage()));
        return WalletConverter.toDto(wallet);
    }
}

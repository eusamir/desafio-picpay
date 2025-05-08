package com.example.picpay_challenger.domain.service;

import com.example.picpay_challenger.domain.model.dto.WalletDto;
import com.example.picpay_challenger.domain.model.entity.Wallet;
import com.example.picpay_challenger.domain.repository.WalletRepository;
import com.example.picpay_challenger.mapper.WalletConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WalletService {
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);
    private WalletRepository walletRepository;

    public void credit(Long id, BigDecimal amount){
        Wallet wallet = walletRepository.findByUserId(id)
                .orElseThrow(()-> new RuntimeException("Carteira não encontrada."));
        BigDecimal currentBalance = wallet.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);


        wallet.setBalance(currentBalance.add(amount));
        walletRepository.save(wallet);

        logger.info("Crédito de {} adicionado à carteira do usuário com ID {}. Saldo atual: {}", amount, id, newBalance);
    }

    public void debit(Long id, BigDecimal amount){
        Wallet wallet = walletRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada."));
        BigDecimal currentBalance = wallet.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);

        if (currentBalance.compareTo(amount)< 0){
            logger.error("Saldo insuficiente para realizar o débito. Saldo atual: {}, valor a ser debitado: {}", currentBalance, amount);
            throw new RuntimeException("Saldo insuficiente.");
        }

        wallet.setBalance(currentBalance.subtract(amount));
        walletRepository.save(wallet);

        logger.info("Crédito de {} retirado da carteira do usuário com ID {}. Saldo atual: {}", amount, id, newBalance);
    }

    public WalletDto getBalance(Long userId){
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(()-> new RuntimeException("Carteira não encontrada."));
        return WalletConverter.toDto(wallet);
    }
}

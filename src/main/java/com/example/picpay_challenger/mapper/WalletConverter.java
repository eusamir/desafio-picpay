package com.example.picpay_challenger.mapper;

import com.example.picpay_challenger.domain.model.dto.WalletDto;
import com.example.picpay_challenger.domain.model.entity.User;
import com.example.picpay_challenger.domain.model.entity.Wallet;

public class WalletConverter {
    public static WalletDto toDto(Wallet wallet){
        return new WalletDto(
                wallet.getId(),
                wallet.getBalance(),
                wallet.getUser().getId()
        );
    }
    public static Wallet toEntity(WalletDto walletDto, User user) {
        return new Wallet(walletDto.getBalance(), user);
    }
}

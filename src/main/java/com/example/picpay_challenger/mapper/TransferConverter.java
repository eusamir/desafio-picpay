package com.example.picpay_challenger.mapper;

import com.example.picpay_challenger.domain.model.dto.TransferDto;
import com.example.picpay_challenger.domain.model.entity.Transfer;
import com.example.picpay_challenger.domain.model.entity.User;

public class TransferConverter {
    public static TransferDto toDto(Transfer transfer){
        return new TransferDto(
                transfer.getId(),
                transfer.getDate(),
                transfer.getAmount(),
                transfer.getSender().getId(),
                transfer.getSender().getName(),
                transfer.getReceiver().getId(),
                transfer.getReceiver().getName()
        );
    }
    public static Transfer toEntity(TransferDto transferDto, User sender, User receiver){
        Transfer transfer = new Transfer();
        transfer.setAmount(transferDto.getAmount());
        transfer.setDate(transferDto.getDate());
        transfer.setSender(sender);
        transfer.setReceiver(receiver);

        return transfer;
    }
}

package com.example.picpay_challenger.domain.model.dto;

import com.example.picpay_challenger.domain.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class WalletDto {
    private UUID id;
    private BigDecimal balance;
    private Long userId;
}

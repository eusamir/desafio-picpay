package com.example.picpay_challenger.domain.model.dto;

import com.example.picpay_challenger.domain.enums.UserType;
import com.example.picpay_challenger.domain.model.entity.Wallet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private UserType type;
    private UUID walletId;
    private BigDecimal balance;
}

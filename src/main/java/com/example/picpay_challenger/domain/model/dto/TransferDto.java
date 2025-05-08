package com.example.picpay_challenger.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TransferDto {
    private UUID id;
    private Timestamp date;
    private BigDecimal amount;
    private Long receiverId;
    private Long senderId;
}

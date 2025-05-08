package com.example.picpay_challenger.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotifyDto {
    private String email;
    private String message;
}

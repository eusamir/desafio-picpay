package com.example.picpay_challenger.controller;

import com.example.picpay_challenger.domain.model.dto.WalletDto;
import com.example.picpay_challenger.domain.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallets")
@AllArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/{id}")
    public ResponseEntity<WalletDto> getBalance(@PathVariable Long id){
        return ResponseEntity.ok(walletService.getBalance(id));
    }
}

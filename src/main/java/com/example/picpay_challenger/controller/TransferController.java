package com.example.picpay_challenger.controller;

import com.example.picpay_challenger.domain.model.dto.TransferDto;
import com.example.picpay_challenger.domain.service.TransferService;
import com.example.picpay_challenger.domain.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
public class TransferController {
    private final TransferService transferService;
    private final WalletService walletService;

    @PostMapping("/new")
    public TransferDto transaction(@RequestBody TransferDto transferDto){
        return transferService.transfer(
                transferDto,
                transferDto.getReceiverId(),
                transferDto.getSenderId()
        );
    }
    @PostMapping("/credit-test")
    public ResponseEntity<String> creditForTest(@RequestParam Long userId, @RequestParam BigDecimal amount) {
        walletService.creditForTest(userId, amount);
        return ResponseEntity.ok("Crédito adicionado com sucesso para testes.");
    }
}

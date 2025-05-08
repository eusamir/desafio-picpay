package com.example.picpay_challenger.controller;

import com.example.picpay_challenger.domain.model.dto.TransferDto;
import com.example.picpay_challenger.domain.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransferController {
    private TransferService transferService;

    @PostMapping("/new")
    public TransferDto transaction(@RequestBody TransferDto transferDto){
        return transferService.transfer(
                transferDto,
                transferDto.getReceiverId(),
                transferDto.getSenderId()
        );
    }
}

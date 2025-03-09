package com.minipay.remittance.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.remittance.application.port.in.RequestRemittanceCommand;
import com.minipay.remittance.application.port.in.RequestRemittanceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RemittanceController {

    private final RequestRemittanceUseCase requestRemittanceUseCase;

    @PostMapping("/remittance")
    ResponseEntity<Void> requestRemittance(@RequestBody RemittanceRequest request) {
        RequestRemittanceCommand command = RequestRemittanceCommand.builder()
                .senderId(request.senderId())
                .recipientId(request.recipientId())
                .destBankName(request.destBankName())
                .destBankAccountNumber(request.destBankAccountNumber())
                .amount(request.amount())
                .build();
        requestRemittanceUseCase.requestRemittance(command);
        return ResponseEntity.ok().build();
    }
}

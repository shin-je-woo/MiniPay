package com.minipay.banking.adapter.in.web.controller;

import com.minipay.banking.adapter.in.web.request.TransferMoneyRequest;
import com.minipay.banking.application.port.in.RequestTransferMoneyUseCase;
import com.minipay.banking.application.port.in.TransferMoneyCommand;
import com.minipay.banking.domain.TransferMoney;
import com.minipay.common.annotation.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class TransferMoneyController {

    private final RequestTransferMoneyUseCase requestTransferMoneyUseCase;

    @PostMapping("/transfer-money")
    ResponseEntity<TransferMoney> transferMoney(@RequestBody TransferMoneyRequest request) {
        TransferMoneyCommand command = TransferMoneyCommand.builder()
                .fromBankName(request.fromBankName())
                .fromBankAccountNumber(request.fromBankAccountNumber())
                .toBankName(request.toBankName())
                .toBankAccountNumber(request.toBankAccountNumber())
                .moneyAmount(request.moneyAmount())
                .build();

        return ResponseEntity.ok(requestTransferMoneyUseCase.requestTransferMoney(command));
    }
}

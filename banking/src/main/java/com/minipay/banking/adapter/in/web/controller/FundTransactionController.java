package com.minipay.banking.adapter.in.web.controller;

import com.minipay.banking.adapter.in.web.request.WithdrawalFundRequest;
import com.minipay.banking.application.port.in.*;
import com.minipay.common.annotation.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FundTransactionController {

    private final WithdrawalFundUseCase withdrawalFundUseCase;
    private final DepositFundUseCase depositFundUseCase;

    @PostMapping("/fund-transactions/withdrawal")
    ResponseEntity<Void> withdrawalFund(@RequestBody WithdrawalFundRequest request) {
        WithdrawalFundCommand command = WithdrawalFundCommand.builder()
                .bankAccountId(request.bankAccountId())
                .bankName(request.bankName())
                .bankAccountNumber(request.bankAccountNumber())
                .amount(request.amount())
                .build();
        withdrawalFundUseCase.withdrawal(command);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/fund-transactions/deposit-axon")
    ResponseEntity<Void> depositFundByAxon(@RequestBody WithdrawalFundRequest request) {
        DepositFundByAxonCommand command = DepositFundByAxonCommand.builder()
                .bankAccountId(request.bankAccountId())
                .amount(request.amount())
                .build();
        depositFundUseCase.depositByAxon(command);

        return ResponseEntity.ok().build();
    }
}

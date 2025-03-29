package com.minipay.banking.adapter.in.web.controller;

import com.minipay.banking.adapter.in.web.request.WithdrawalFundRequest;
import com.minipay.banking.application.port.in.WithdrawalFundUseCase;
import com.minipay.banking.application.port.in.WithdrawalFundCommand;
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
}

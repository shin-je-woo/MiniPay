package com.minipay.banking.adapter.in.web.controller;

import com.minipay.banking.adapter.in.web.request.WithdrawalMinipayFundRequest;
import com.minipay.banking.application.port.in.WithdrawalMinipayFundUseCase;
import com.minipay.banking.application.port.in.WithdrawalMinipayMoneyCommand;
import com.minipay.common.annotation.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class MinipayFundController {

    private final WithdrawalMinipayFundUseCase withdrawalMinipayFundUseCase;

    @PostMapping("/minipay-funds/withdrawal")
    ResponseEntity<Void> withdrawalMinipayFund(@RequestBody WithdrawalMinipayFundRequest request) {
        WithdrawalMinipayMoneyCommand command = WithdrawalMinipayMoneyCommand.builder()
                .bankAccountId(request.bankAccountId())
                .bankName(request.bankName())
                .bankAccountNumber(request.bankAccountNumber())
                .amount(request.amount())
                .build();
        withdrawalMinipayFundUseCase.withdrawal(command);

        return ResponseEntity.ok().build();
    }
}

package com.minipay.banking.adapter.in.web;

import com.minipay.banking.application.port.in.RegisterBankAccountCommand;
import com.minipay.banking.application.port.in.RegisterBankAccountUseCase;
import com.minipay.banking.domain.BankAccount;
import com.minipay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {

    private final RegisterBankAccountUseCase registerBankAccountUseCase;

    @PostMapping("/bank-accounts")
    ResponseEntity<BankAccount> registerBankAccount(@RequestBody RegisterBankAccountRequest request) {
        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .ownerId(request.membershipId())
                .bankName(request.bankName())
                .bankAccountNumber(request.bankAccountNumber())
                .build();

        return ResponseEntity.ok(registerBankAccountUseCase.registerBankAccount(command));
    }
}

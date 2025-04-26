package com.minipay.banking.adapter.in.web.controller;

import com.minipay.banking.adapter.in.web.request.RegisterBankAccountRequest;
import com.minipay.banking.adapter.in.web.response.BankAccountListResponse;
import com.minipay.banking.adapter.in.web.response.BankAccountResponse;
import com.minipay.banking.application.port.in.GetBankAccountUseCase;
import com.minipay.banking.application.port.in.RegisterBankAccountCommand;
import com.minipay.banking.application.port.in.RegisterBankAccountUseCase;
import com.minipay.banking.domain.model.BankAccount;
import com.minipay.banking.domain.model.ExternalBankAccount;
import com.minipay.common.annotation.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class BankAccountController {

    private final RegisterBankAccountUseCase registerBankAccountUseCase;
    private final GetBankAccountUseCase getBankAccountUseCase;

    @PostMapping("/bank-accounts")
    ResponseEntity<BankAccount> registerBankAccount(@RequestBody RegisterBankAccountRequest request) {
        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .membershipId(request.membershipId())
                .bankName(request.bankName())
                .bankAccountNumber(request.bankAccountNumber())
                .build();

        return ResponseEntity.ok(registerBankAccountUseCase.registerBankAccount(command));
    }

    @PostMapping("/bank-accounts/axon")
    ResponseEntity<Void> registerBankAccountByAxon(@RequestBody RegisterBankAccountRequest request) {
        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .membershipId(request.membershipId())
                .bankName(request.bankName())
                .bankAccountNumber(request.bankAccountNumber())
                .build();

        registerBankAccountUseCase.registerBankAccountByAxon(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping("bank-accounts/{bankAccountId}")
    ResponseEntity<BankAccountResponse> getBankAccount(@PathVariable UUID bankAccountId) {
        BankAccount bankAccount = getBankAccountUseCase.getBankAccount(new BankAccount.BankAccountId(bankAccountId));
        return ResponseEntity.ok(BankAccountResponse.from(bankAccount));
    }

    @GetMapping("/bank-accounts")
    ResponseEntity<BankAccountListResponse> getBankAccountsByBankName(@RequestParam String bankName) {
        List<BankAccount> bankAccounts = getBankAccountUseCase.getBankAccountsByBankName(new ExternalBankAccount.BankName(bankName));
        return ResponseEntity.ok(BankAccountListResponse.from(bankAccounts));
    }
}

package com.minipay.banking.adapter.in.web.response;

import com.minipay.banking.domain.model.BankAccount;

import java.util.List;

public record BankAccountListResponse(
        List<BankAccountResponse> bankAccounts
) {
    public static BankAccountListResponse from(List<BankAccount> bankAccounts) {
        List<BankAccountResponse> bankAccountResponses = bankAccounts.stream()
                .map(BankAccountResponse::from)
                .toList();
        return new BankAccountListResponse(bankAccountResponses);
    }
}

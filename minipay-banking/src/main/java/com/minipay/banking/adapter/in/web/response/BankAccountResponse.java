package com.minipay.banking.adapter.in.web.response;

import com.minipay.banking.domain.model.BankAccount;

import java.util.UUID;

public record BankAccountResponse(
        UUID bankAccountId,
        UUID membershipId,
        String linkedBankName,
        String linkedAccountNumber,
        BankAccount.LinkedStatus linkedStatus
) {
    public static BankAccountResponse from(BankAccount bankAccount) {
        return new BankAccountResponse(
                bankAccount.getBankAccountId().value(),
                bankAccount.getMembershipId().value(),
                bankAccount.getLinkedBankAccount().bankName().value(),
                bankAccount.getLinkedBankAccount().accountNumber().value(),
                bankAccount.getLinkedStatus()
        );
    }
}

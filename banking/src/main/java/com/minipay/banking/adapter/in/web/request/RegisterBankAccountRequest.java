package com.minipay.banking.adapter.in.web.request;

public record RegisterBankAccountRequest(
        Long membershipId,
        String bankName,
        String bankAccountNumber
) {
}

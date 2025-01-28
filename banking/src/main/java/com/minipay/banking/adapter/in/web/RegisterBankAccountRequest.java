package com.minipay.banking.adapter.in.web;

public record RegisterBankAccountRequest(
        Long membershipId,
        String bankName,
        String bankAccountNumber
) {
}

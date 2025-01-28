package com.minipay.banking.application.port.out;

public record BankAccountInfo(
        String bankName,
        String accountNumber,
        boolean isValid
) {
}


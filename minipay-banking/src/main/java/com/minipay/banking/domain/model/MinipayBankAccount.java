package com.minipay.banking.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MinipayBankAccount {

    CORPORATE_ACCOUNT("미니페이은행", "123-111-23421");

    private final String bankName;
    private final String accountNumber;
}

package com.minipay.banking.domain.model;

import com.minipay.common.exception.DomainRuleException;
import org.springframework.util.StringUtils;

public record ExternalBankAccount(
        BankName bankName,
        AccountNumber accountNumber
) {
    public record BankName(String value) {
        public BankName {
            if (!StringUtils.hasText(value)) {
                throw new IllegalArgumentException("Bank name cannot be empty.");
            }
        }
    }

    public record AccountNumber(String value) {
        public AccountNumber {
            if (!StringUtils.hasText(value)) {
                throw new IllegalArgumentException("Account number cannot be empty.");
            }
            if (!value.matches("\\d{10,16}")) {
                throw new DomainRuleException("Invalid bank account number");
            }
        }
    }
}

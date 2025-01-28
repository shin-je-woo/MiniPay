package com.minipay.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankAccount {
    private final BankAccountId bankAccountId;
    private final OwnerId ownerId;
    private final String bankName;
    private final String accountNumber;
    private final boolean linkedStatusIsValid;

    public static BankAccount create(
            OwnerId ownerId,
            String bankName,
            String accountNumber,
            boolean linkedStatusIsValid
    ) {
        return new BankAccount(null, ownerId, bankName, accountNumber, linkedStatusIsValid);
    }

    public static BankAccount withId(
            BankAccountId bankAccountId,
            OwnerId ownerId,
            String bankName,
            String accountNumber,
            boolean linkedStatusIsValid
    ) {
        return new BankAccount(bankAccountId, ownerId, bankName, accountNumber, linkedStatusIsValid);
    }

    public record BankAccountId(Long value) {
    }

    public record OwnerId(Long value) {
    }
}

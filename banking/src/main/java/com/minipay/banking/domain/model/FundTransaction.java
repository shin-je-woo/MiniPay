package com.minipay.banking.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FundTransaction {

    @EqualsAndHashCode.Include
    private final FundTransactionId fundTransactionId;
    private final BankAccount.BankAccountId bankAccountId;
    private final MinipayBankAccount minipayBankAccount;
    private final FundType fundType;
    private FundTransactionStatus status;
    private final Money amount;

    // Factory
    public static FundTransaction depositInstance(
            BankAccount.BankAccountId bankAccountId,
            Money money
    ) {
        return new FundTransaction(FundTransactionId.generate(), bankAccountId, MinipayBankAccount.NORMAL_ACCOUNT, FundType.DEPOSIT, FundTransactionStatus.REQUESTED, money);
    }

    public static FundTransaction withdrawalInstance(
            BankAccount.BankAccountId bankAccountId,
            Money money
    ) {
        return new FundTransaction(FundTransactionId.generate(), bankAccountId, MinipayBankAccount.NORMAL_ACCOUNT, FundType.WITHDRAWAL, FundTransactionStatus.REQUESTED, money);
    }

    public static FundTransaction withId(
            FundTransactionId fundTransactionId,
            BankAccount.BankAccountId bankAccountId,
            MinipayBankAccount minipayBankAccount,
            FundType fundType,
            FundTransactionStatus status,
            Money money
    ) {
        return new FundTransaction(fundTransactionId, bankAccountId, minipayBankAccount, fundType, status, money);
    }

    // VO
    public record FundTransactionId(UUID value) {
        public FundTransactionId {
            if (value == null) {
                throw new IllegalArgumentException("FundTransactionId is null");
            }
        }

        private static FundTransactionId generate() {
            return new FundTransactionId(UUID.randomUUID());
        }
    }

    public enum FundType {
        DEPOSIT,
        WITHDRAWAL
    }

    public enum FundTransactionStatus {
        REQUESTED,
        SUCCEEDED,
        FAILED
    }

    // Logic
    public void success() {
        if (this.status != FundTransactionStatus.REQUESTED) {
            throw new IllegalStateException("FundTransaction status is not REQUESTED");
        }
        this.status = FundTransactionStatus.SUCCEEDED;
    }

    public void fail() {
        if (this.status != FundTransactionStatus.REQUESTED) {
            throw new IllegalStateException("FundTransaction status is not REQUESTED");
        }
        this.status = FundTransactionStatus.FAILED;
    }
}

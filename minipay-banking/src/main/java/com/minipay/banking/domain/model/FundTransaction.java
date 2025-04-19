package com.minipay.banking.domain.model;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FundTransaction {

    @EqualsAndHashCode.Include
    private final FundTransactionId fundTransactionId;
    private final BankAccount.BankAccountId bankAccountId;
    private final MinipayBankAccount minipayBankAccount;
    @Nullable
    private final ExternalBankAccount withdrawalBankAccount;
    private final FundType fundType;
    private final Money amount;
    private FundTransactionStatus status;

    // Factory
    public static FundTransaction depositInstance(
            BankAccount.BankAccountId bankAccountId,
            Money money
    ) {
        return new FundTransaction(
                FundTransactionId.generate(),
                bankAccountId,
                MinipayBankAccount.CORPORATE_ACCOUNT,
                null,
                FundType.DEPOSIT,
                money,
                FundTransactionStatus.REQUESTED
        );
    }

    public static FundTransaction withdrawalInstance(
            BankAccount.BankAccountId bankAccountId,
            ExternalBankAccount withdrawalBankAccount,
            Money money
    ) {
        return new FundTransaction(
                FundTransactionId.generate(),
                bankAccountId,
                MinipayBankAccount.CORPORATE_ACCOUNT,
                withdrawalBankAccount,
                FundType.WITHDRAWAL,
                money,
                FundTransactionStatus.REQUESTED
        );
    }

    public static FundTransaction withId(
            FundTransactionId fundTransactionId,
            BankAccount.BankAccountId bankAccountId,
            MinipayBankAccount minipayBankAccount,
            ExternalBankAccount withdrawalBankAccount,
            FundType fundType,
            Money money,
            FundTransactionStatus status
    ) {
        return new FundTransaction(fundTransactionId, bankAccountId, minipayBankAccount, withdrawalBankAccount, fundType, money, status);
    }

    // VO
    public record FundTransactionId(UUID value) {
        public FundTransactionId {
            if (value == null) {
                throw new IllegalArgumentException("fundTransactionId is null");
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

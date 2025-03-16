package com.minipay.banking.domain;

import lombok.*;

import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MinipayFund {

    @EqualsAndHashCode.Include
    private final MinipayFundId minipayFundId;
    private final BankAccount.BankAccountId bankAccountId;
    private final MinipayBankAccount minipayBankAccount;
    private final FundType fundType;
    private final Money amount;

    // Factory
    public static MinipayFund depositInstance(
            BankAccount.BankAccountId bankAccountId,
            Money money
    ) {
        return new MinipayFund(MinipayFundId.generate(), bankAccountId, MinipayBankAccount.NORMAL_ACCOUNT, FundType.DEPOSIT, money);
    }

    public static MinipayFund withdrawalInstance(
            BankAccount.BankAccountId bankAccountId,
            Money money
    ) {
        return new MinipayFund(MinipayFundId.generate(), bankAccountId, MinipayBankAccount.NORMAL_ACCOUNT, FundType.WITHDRAWAL, money);
    }

    public static MinipayFund withId(
            MinipayFundId minipayFundId,
            BankAccount.BankAccountId bankAccountId,
            MinipayBankAccount minipayBankAccount,
            FundType fundType,
            Money money
    ) {
        return new MinipayFund(minipayFundId, bankAccountId, minipayBankAccount, fundType, money);
    }

    // VO
    public record MinipayFundId(UUID value) {
        public MinipayFundId {
            if (value == null) {
                throw new IllegalArgumentException("Minipay fund id is null");
            }
        }

        private static MinipayFundId generate() {
            return new MinipayFundId(UUID.randomUUID());
        }
    }

    public enum FundType {
        DEPOSIT,
        WITHDRAWAL
    }
}

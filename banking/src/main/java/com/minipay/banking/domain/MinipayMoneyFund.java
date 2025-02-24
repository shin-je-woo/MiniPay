package com.minipay.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MinipayMoneyFund {

    private final MinipayMoneyFundId minipayMoneyFundId;
    private final BankAccount.BankAccountId bankAccountId;
    private final MoneyHistoryId moneyHistoryId;
    private final FundType fundType;
    private final Money amount;
    private final Money afterBalance;

    // Factory
    public static MinipayMoneyFund newInstance(
            @NonNull BankAccount.BankAccountId bankAccountId,
            @NonNull MoneyHistoryId moneyHistoryId,
            @NonNull FundType fundType,
            @NonNull Money money,
            @NonNull Money afterBalance
    ) {
        return new MinipayMoneyFund(MinipayMoneyFundId.generate(), bankAccountId, moneyHistoryId, fundType, money, afterBalance);
    }

    // VO
    public record MinipayMoneyFundId(UUID value) {
        public MinipayMoneyFundId {
            if (value == null) {
                throw new IllegalArgumentException("Minipay fund id is null");
            }
        }

        private static MinipayMoneyFundId generate() {
            return new MinipayMoneyFundId(UUID.randomUUID());
        }
    }

    public record MoneyHistoryId(UUID value) {
        public MoneyHistoryId {
            if (value == null) {
                throw new IllegalArgumentException("Money history id is null");
            }
        }
    }

    public enum FundType {
        DEPOSIT,
        WITHDRAWAL
    }
}

package com.minipay.banking.domain;

import com.minipay.common.event.EventType;
import com.minipay.common.event.Events;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MinipayFund {

    private final MinipayFundId minipayFundId;
    private final BankAccount.BankAccountId bankAccountId;
    private final MinipayBankAccount minipayBankAccount;
    private final FundType fundType;
    private final Money amount;

    // Factory
    public static MinipayFund depositInstance(
            @NonNull BankAccount.BankAccountId bankAccountId,
            @NonNull Money money
    ) {
        MinipayFund minipayFund = new MinipayFund(MinipayFundId.generate(), bankAccountId, MinipayBankAccount.NORMAL_ACCOUNT, FundType.DEPOSIT, money);
        Events.raise(MinipayFundEvent.of(EventType.MINIPAY_FUND_DEPOSITED, minipayFund));
        
        return minipayFund;
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

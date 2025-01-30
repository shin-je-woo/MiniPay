package com.minipay.money.domain;

import com.minipay.common.DomainRuleException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyHistory {

    private final MoneyHistoryId moneyHistoryId;
    private final MemberMoney.MemberMoneyId memberMoneyId;
    private final ChangeType changeType;
    private final Money amount;
    private final Money afterBalance;
    private final LocalDateTime createdAt;

    public static MoneyHistory create(
            MemberMoney.MemberMoneyId memberMoneyId,
            ChangeType changeType,
            Money amount,
            Money currentBalance
    ) {
        if (changeType == null) {
            throw new DomainRuleException("changeType can't be null");
        }

        Money afterBalance = changeType == ChangeType.INCREASE
                ? currentBalance.add(amount)
                : currentBalance.subtract(amount);

        return new MoneyHistory(null, memberMoneyId, changeType, amount, afterBalance, LocalDateTime.now());
    }

    public static MoneyHistory withId(
            MoneyHistoryId moneyHistoryId,
            MemberMoney.MemberMoneyId memberMoneyId,
            ChangeType changeType,
            Money amount,
            Money afterBalance,
            LocalDateTime createdAt
    ) {
        return new MoneyHistory(moneyHistoryId, memberMoneyId, changeType, amount, afterBalance, createdAt);
    }

    public record MoneyHistoryId(Long value) {
        public MoneyHistoryId {
            if (value == null) {
                throw new DomainRuleException("MoneyHistoryId can't be null");
            }
        }
    }

    public enum ChangeType {
        INCREASE,
        DECREASE
    }
}

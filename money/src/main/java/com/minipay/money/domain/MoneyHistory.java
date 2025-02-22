package com.minipay.money.domain;

import com.minipay.common.exception.DomainRuleException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyHistory {

    private final MoneyHistoryId moneyHistoryId;
    private final MemberMoney.MemberMoneyId memberMoneyId;
    private final ChangeType changeType;
    private final ChangeStatus changeStatus;
    private final Money amount;
    private final Money afterBalance;
    private final LocalDateTime createdAt;

    // Factory
    public static MoneyHistory newInstance(
            MemberMoney.MemberMoneyId memberMoneyId,
            ChangeType changeType,
            Money amount,
            Money afterBalance
    ) {
        if (changeType == null) {
            throw new DomainRuleException("changeType can't be null");
        }

        return new MoneyHistory(MoneyHistoryId.generate(), memberMoneyId, changeType, ChangeStatus.REQUESTED, amount, afterBalance, LocalDateTime.now());
    }

    public static MoneyHistory withId(
            MoneyHistoryId moneyHistoryId,
            MemberMoney.MemberMoneyId memberMoneyId,
            ChangeType changeType,
            ChangeStatus changeStatus,
            Money amount,
            Money afterBalance,
            LocalDateTime createdAt
    ) {
        return new MoneyHistory(moneyHistoryId, memberMoneyId, changeType, changeStatus, amount, afterBalance, createdAt);
    }

    // VO
    public record MoneyHistoryId(UUID value) {
        public MoneyHistoryId {
            if (value == null) {
                throw new DomainRuleException("MoneyHistoryId can't be null");
            }
        }

        private static MoneyHistoryId generate() {
            return new MoneyHistoryId(UUID.randomUUID());
        }
    }

    public enum ChangeType {
        INCREASE,
        DECREASE
    }

    public enum ChangeStatus {
        REQUESTED,
        SUCCEED,
        FAILED
    }
}

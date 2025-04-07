package com.minipay.money.domain.model;

import com.minipay.common.exception.DomainRuleException;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(access = AccessLevel.PRIVATE)
public class MoneyHistory {

    @EqualsAndHashCode.Include
    private final MoneyHistoryId moneyHistoryId;
    private final MemberMoney.MemberMoneyId memberMoneyId;
    private final ChangeType changeType;
    private final Money amount;
    private final LocalDateTime createdAt;
    private ChangeStatus changeStatus;
    private Money afterBalance;
    private LocalDateTime updatedAt;

    // Factory
    public static MoneyHistory newInstance(
            MemberMoney.MemberMoneyId memberMoneyId,
            ChangeType changeType,
            Money amount
    ) {
        LocalDateTime now = LocalDateTime.now();
        return MoneyHistory.builder()
                .moneyHistoryId(MoneyHistoryId.generate())
                .memberMoneyId(memberMoneyId)
                .changeType(changeType)
                .amount(amount)
                .createdAt(now)
                .changeStatus(ChangeStatus.REQUESTED)
                .afterBalance(null)
                .updatedAt(now)
                .build();
    }

    public static MoneyHistory withId(
            MoneyHistoryId moneyHistoryId,
            MemberMoney.MemberMoneyId memberMoneyId,
            ChangeType changeType,
            Money amount,
            LocalDateTime createdAt,
            ChangeStatus changeStatus,
            Money afterBalance,
            LocalDateTime updatedAt
    ) {
        return MoneyHistory.builder()
                .moneyHistoryId(moneyHistoryId)
                .memberMoneyId(memberMoneyId)
                .changeType(changeType)
                .amount(amount)
                .createdAt(createdAt)
                .changeStatus(changeStatus)
                .afterBalance(afterBalance)
                .updatedAt(updatedAt)
                .build();
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
        RECHARGE,
        INCREASE,
        DECREASE
    }

    public enum ChangeStatus {
        REQUESTED,
        SUCCEED,
        FAILED,
        FAILED_NOT_VALID_ACCOUNT
    }

    // Logic
    public void succeed(MemberMoney memberMoney) {
        this.changeStatus = ChangeStatus.SUCCEED;
        this.afterBalance = memberMoney.getBalance();
        this.updatedAt = LocalDateTime.now();
    }

    // Logic
    public void failNotValidAccount() {
        if (this.changeStatus != ChangeStatus.REQUESTED) {
            throw new DomainRuleException("MoneyHistory is not in REQUESTED status");
        }
        this.changeStatus = ChangeStatus.FAILED_NOT_VALID_ACCOUNT;
        this.updatedAt = LocalDateTime.now();
    }
}

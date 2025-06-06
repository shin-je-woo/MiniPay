package com.minipay.money.adapter.out.persistence.mapper;

import com.minipay.common.annotation.DomainMapper;
import com.minipay.money.adapter.out.persistence.entity.MoneyHistoryJpaEntity;
import com.minipay.money.domain.model.MemberMoney;
import com.minipay.money.domain.model.Money;
import com.minipay.money.domain.model.MoneyHistory;

import java.util.Optional;

@DomainMapper
public class MoneyHistoryMapper {

    public MoneyHistory mapToDomain(MoneyHistoryJpaEntity moneyHistory) {
        return MoneyHistory.withId(
                new MoneyHistory.MoneyHistoryId(moneyHistory.getMoneyHistoryId()),
                new MemberMoney.MemberMoneyId(moneyHistory.getMemberMoneyId()),
                moneyHistory.getChangeType(),
                new Money(moneyHistory.getAmount()),
                moneyHistory.getCreatedAt(),
                moneyHistory.getChangeStatus(),
                Optional.ofNullable(moneyHistory.getAfterBalance()).map(Money::new).orElse(null),
                moneyHistory.getUpdatedAt()
        );
    }

    public MoneyHistoryJpaEntity mapToJpaEntity(MoneyHistory moneyHistory) {
        return MoneyHistoryJpaEntity.builder()
                .moneyHistoryId(moneyHistory.getMoneyHistoryId().value())
                .memberMoneyId(moneyHistory.getMemberMoneyId().value())
                .changeType(moneyHistory.getChangeType())
                .changeStatus(moneyHistory.getChangeStatus())
                .amount(moneyHistory.getAmount().value())
                .afterBalance(Optional.ofNullable(moneyHistory.getAfterBalance()).map(Money::value).orElse(null))
                .createdAt(moneyHistory.getCreatedAt())
                .updatedAt(moneyHistory.getUpdatedAt())
                .build();
    }

    public MoneyHistoryJpaEntity mapToExistingJpaEntity(MoneyHistory moneyHistory, Long jpaEntityId) {
        return MoneyHistoryJpaEntity.builder()
                .id(jpaEntityId)
                .moneyHistoryId(moneyHistory.getMoneyHistoryId().value())
                .memberMoneyId(moneyHistory.getMemberMoneyId().value())
                .changeType(moneyHistory.getChangeType())
                .changeStatus(moneyHistory.getChangeStatus())
                .amount(moneyHistory.getAmount().value())
                .afterBalance(Optional.ofNullable(moneyHistory.getAfterBalance()).map(Money::value).orElse(null))
                .createdAt(moneyHistory.getCreatedAt())
                .updatedAt(moneyHistory.getUpdatedAt())
                .build();
    }
}

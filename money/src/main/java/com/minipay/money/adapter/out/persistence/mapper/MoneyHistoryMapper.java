package com.minipay.money.adapter.out.persistence.mapper;

import com.minipay.common.annotation.DomainMapper;
import com.minipay.money.adapter.out.persistence.entity.MoneyHistoryJpaEntity;
import com.minipay.money.domain.MemberMoney;
import com.minipay.money.domain.Money;
import com.minipay.money.domain.MoneyHistory;

@DomainMapper
public class MoneyHistoryMapper {

    public MoneyHistory mapToDomain(MoneyHistoryJpaEntity moneyHistory) {
        return MoneyHistory.withId(
                new MoneyHistory.MoneyHistoryId(moneyHistory.getMoneyHistoryId()),
                new MemberMoney.MemberMoneyId(moneyHistory.getMemberMoneyId()),
                moneyHistory.getChangeType(),
                moneyHistory.getChangeStatus(),
                new Money(moneyHistory.getAmount()),
                new Money(moneyHistory.getAfterBalance()),
                moneyHistory.getCreatedAt()
        );
    }

    public MoneyHistoryJpaEntity mapToJpaEntity(MoneyHistory moneyHistory) {
        return MoneyHistoryJpaEntity.builder()
                .moneyHistoryId(moneyHistory.getMoneyHistoryId().value())
                .memberMoneyId(moneyHistory.getMemberMoneyId().value())
                .changeType(moneyHistory.getChangeType())
                .changeStatus(moneyHistory.getChangeStatus())
                .amount(moneyHistory.getAmount().value())
                .afterBalance(moneyHistory.getAfterBalance().value())
                .createdAt(moneyHistory.getCreatedAt())
                .build();
    }
}

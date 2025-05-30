package com.minipay.money.domain.event;

import com.minipay.common.event.AggregateType;
import com.minipay.common.event.DomainEvent;
import com.minipay.common.event.EventType;
import com.minipay.money.domain.model.MoneyHistory;
import com.minipay.money.domain.model.MemberMoney;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

public class MemberMoneyEvent extends DomainEvent {

    private MemberMoneyEvent(EventType eventType, UUID aggregateId, Payload payload) {
        super(eventType, AggregateType.MEMBER_MONEY, aggregateId, payload);
    }

    public static MemberMoneyEvent of(EventType eventType, MemberMoney memberMoney, MoneyHistory moneyHistory) {
        Payload payload = Payload.builder()
                .memberMoneyId(memberMoney.getMemberMoneyId().value())
                .bankAccountId(memberMoney.getBankAccountId().value())
                .moneyHistoryId(moneyHistory.getMoneyHistoryId().value())
                .amount(moneyHistory.getAmount().value())
                .build();

        return new MemberMoneyEvent(
                eventType,
                memberMoney.getMemberMoneyId().value(),
                payload
        );
    }

    @Builder
    public record Payload(
            UUID memberMoneyId,
            UUID bankAccountId,
            UUID moneyHistoryId,
            BigDecimal amount
    ) {
    }
}

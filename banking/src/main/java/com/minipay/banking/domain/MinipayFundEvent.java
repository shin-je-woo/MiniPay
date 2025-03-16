package com.minipay.banking.domain;

import com.minipay.common.event.AggregateType;
import com.minipay.common.event.DomainEvent;
import com.minipay.common.event.EventType;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

public class MinipayFundEvent extends DomainEvent {

    private MinipayFundEvent(EventType eventType, UUID aggregateId, MinipayFundEvent.Payload payload) {
        super(eventType, AggregateType.MINIPAY_FUND, aggregateId, payload);
    }

    public static MinipayFundEvent of(EventType eventType, MinipayFund minipayFund, UUID moneyHistoryId) {
        MinipayFundEvent.Payload payload = Payload.builder()
                .minipayFundId(minipayFund.getMinipayFundId().value())
                .bankAccountId(minipayFund.getBankAccountId().value())
                .moneyHistoryId(moneyHistoryId)
                .amount(minipayFund.getAmount().value())
                .fundType(minipayFund.getFundType())
                .build();

        return new MinipayFundEvent(
                eventType,
                minipayFund.getMinipayFundId().value(),
                payload
        );
    }

    public static MinipayFundEvent of(EventType eventType, MinipayFund minipayFund) {
        MinipayFundEvent.Payload payload = Payload.builder()
                .minipayFundId(minipayFund.getMinipayFundId().value())
                .bankAccountId(minipayFund.getBankAccountId().value())
                .amount(minipayFund.getAmount().value())
                .fundType(minipayFund.getFundType())
                .build();

        return new MinipayFundEvent(
                eventType,
                minipayFund.getMinipayFundId().value(),
                payload
        );
    }

    @Builder
    public record Payload(
            UUID minipayFundId,
            UUID bankAccountId,
            UUID moneyHistoryId,
            BigDecimal amount,
            MinipayFund.FundType fundType
    ) {
    }
}

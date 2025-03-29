package com.minipay.banking.domain.event;

import com.minipay.banking.domain.model.FundTransaction;
import com.minipay.common.event.AggregateType;
import com.minipay.common.event.DomainEvent;
import com.minipay.common.event.EventType;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

public class FundTransactionEvent extends DomainEvent {

    private FundTransactionEvent(EventType eventType, UUID aggregateId, FundTransactionEvent.Payload payload) {
        super(eventType, AggregateType.MINIPAY_FUND, aggregateId, payload);
    }

    public static FundTransactionEvent of(EventType eventType, FundTransaction fundTransaction, UUID moneyHistoryId) {
        FundTransactionEvent.Payload payload = Payload.builder()
                .fundTransactionId(fundTransaction.getFundTransactionId().value())
                .bankAccountId(fundTransaction.getBankAccountId().value())
                .moneyHistoryId(moneyHistoryId)
                .amount(fundTransaction.getAmount().value())
                .fundType(fundTransaction.getFundType())
                .build();

        return new FundTransactionEvent(
                eventType,
                fundTransaction.getFundTransactionId().value(),
                payload
        );
    }

    public static FundTransactionEvent of(EventType eventType, FundTransaction fundTransaction) {
        FundTransactionEvent.Payload payload = Payload.builder()
                .fundTransactionId(fundTransaction.getFundTransactionId().value())
                .bankAccountId(fundTransaction.getBankAccountId().value())
                .amount(fundTransaction.getAmount().value())
                .fundType(fundTransaction.getFundType())
                .build();

        return new FundTransactionEvent(
                eventType,
                fundTransaction.getFundTransactionId().value(),
                payload
        );
    }

    @Builder
    public record Payload(
            UUID fundTransactionId,
            UUID bankAccountId,
            UUID moneyHistoryId,
            BigDecimal amount,
            FundTransaction.FundType fundType
    ) {
    }
}

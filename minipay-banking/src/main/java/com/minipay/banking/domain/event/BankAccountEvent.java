package com.minipay.banking.domain.event;

import com.minipay.banking.domain.model.BankAccount;
import com.minipay.common.event.AggregateType;
import com.minipay.common.event.EventType;
import com.minipay.common.event.DomainEvent;
import lombok.*;

import java.util.UUID;

public class BankAccountEvent extends DomainEvent {

    private BankAccountEvent(EventType eventType, UUID aggregateId, Payload payload) {
        super(eventType, AggregateType.BANK_ACCOUNT, aggregateId, payload);
    }

    public static BankAccountEvent of(EventType eventType, BankAccount bankAccount) {
        Payload payload = Payload.builder()
                .bankAccountId(bankAccount.getBankAccountId().value())
                .membershipId(bankAccount.getMembershipId().value())
                .bankName(bankAccount.getLinkedBankAccount().bankName().value())
                .accountNumber(bankAccount.getLinkedBankAccount().accountNumber().value())
                .build();

        return new BankAccountEvent(
                eventType,
                bankAccount.getBankAccountId().value(),
                payload
        );
    }

    @Builder
    public record Payload(
            UUID bankAccountId,
            UUID membershipId,
            String bankName,
            String accountNumber
    ) {
    }
}

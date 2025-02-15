package com.minipay.banking.domain;

import com.minipay.common.event.EventType;
import com.minipay.common.event.DomainEvent;
import lombok.*;

import java.util.UUID;

@Getter
public class BankAccountEvent extends DomainEvent {

    private BankAccountEvent(EventType eventType, String aggregateId, Payload payload) {
        super(eventType.getValue(), "BankAccount", aggregateId, payload);
    }

    public static BankAccountEvent of(EventType eventType, BankAccount bankAccount) {
        Payload payload = Payload.builder()
                .bankAccountUuid(bankAccount.getUuid())
                .membershipId(bankAccount.getOwnerId().value())
                .bankName(bankAccount.getLinkedBankAccount().bankName())
                .accountNumber(bankAccount.getLinkedBankAccount().accountNumber())
                .build();

        return new BankAccountEvent(
                eventType,
                String.valueOf(bankAccount.getUuid()),
                payload
        );
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Payload {
        private UUID bankAccountUuid;
        private Long membershipId;
        private String bankName;
        private String accountNumber;
    }
}

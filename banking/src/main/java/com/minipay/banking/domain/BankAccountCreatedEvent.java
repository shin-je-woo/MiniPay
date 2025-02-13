package com.minipay.banking.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.minipay.common.event.DomainEvent;
import com.minipay.common.event.EventType;
import lombok.*;

import java.util.UUID;

@Getter
public class BankAccountCreatedEvent extends DomainEvent {

    private BankAccountCreatedEvent(UUID aggregateId, Payload payload) {
        super(EventType.BANK_ACCOUNT_CREATED, "BankAccount", String.valueOf(aggregateId), payload);
    }

    public static BankAccountCreatedEvent of(BankAccount bankAccount) {
        Payload payload = Payload.builder()
                .bankAccountUuid(bankAccount.getUuid())
                .membershipId(bankAccount.getOwnerId().value())
                .bankName(bankAccount.getLinkedBankAccount().bankName())
                .accountNumber(bankAccount.getLinkedBankAccount().accountNumber())
                .build();

        return new BankAccountCreatedEvent(
                bankAccount.getUuid(),
                payload
        );
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class Payload {
        private UUID bankAccountUuid;
        private Long membershipId;
        private String bankName;
        private String accountNumber;
    }
}

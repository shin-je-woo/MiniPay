package com.minipay.money.adapter.in.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minipay.common.constants.Topic;
import com.minipay.common.event.DomainEvent;
import com.minipay.common.event.EventType;
import com.minipay.money.application.port.in.RegisterMemberMoneyCommand;
import com.minipay.money.application.port.in.RegisterMemberMoneyUseCase;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BankAccountEventConsumer {

    private final ObjectMapper objectMapper;
    private final RegisterMemberMoneyUseCase registerMemberMoneyUseCase;

    @KafkaListener(
            topics = {Topic.BANK_ACCOUNT_EVENTS},
            groupId = "money.member-money.register",
            concurrency = "3"
    )
    public void registerMemberMoney(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) throws JsonProcessingException {
        String message = record.value();
        DomainEvent domainEvent = objectMapper.readValue(message, DomainEvent.class);
        BankAccountEventPayload payload = objectMapper.convertValue(domainEvent.getPayload(), BankAccountEventPayload.class);

        if (domainEvent.getEventType() == EventType.BANK_ACCOUNT_CREATED) {
            handleCreateEvent(payload);
        }

        acknowledgment.acknowledge();
    }

    private void handleCreateEvent(BankAccountEventPayload payload) {
        RegisterMemberMoneyCommand command = RegisterMemberMoneyCommand.builder()
                .membershipId(payload.membershipId())
                .bankAccountId(payload.bankAccountId())
                .build();
        registerMemberMoneyUseCase.registerMemberMoney(command);
    }
}

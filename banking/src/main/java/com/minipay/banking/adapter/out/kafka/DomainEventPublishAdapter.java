package com.minipay.banking.adapter.out.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minipay.banking.application.port.out.DomainEventPublishPort;
import com.minipay.common.constants.Topic;
import com.minipay.common.event.AggregateType;
import com.minipay.common.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DomainEventPublishAdapter implements DomainEventPublishPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(DomainEvent domainEvent) {
        try {
            kafkaTemplate.send(
                    generateTopic(domainEvent.getAggregateType()),
                    domainEvent.getAggregateId().toString(),
                    objectMapper.writeValueAsString(domainEvent)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateTopic(AggregateType aggregateType) {
        return switch (aggregateType) {
            case BANK_ACCOUNT -> Topic.BANK_ACCOUNT_EVENTS;
            case MINIPAY_FUND -> Topic.FUND_TRANSACTION_EVENTS;
            default -> Topic.UNKNOWN;
        };
    }
}

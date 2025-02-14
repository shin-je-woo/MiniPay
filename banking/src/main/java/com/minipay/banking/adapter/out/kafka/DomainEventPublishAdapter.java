package com.minipay.banking.adapter.out.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minipay.banking.application.port.out.DomainEventPublishPort;
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
                    domainEvent.getEventType(),
                    domainEvent.getAggregateId(),
                    objectMapper.writeValueAsString(domainEvent)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

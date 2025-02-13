package com.minipay.common.event;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OutboxPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final DomainEventPersistencePort domainEventPersistencePort;
    private final ObjectMapper objectMapper;

    @Transactional
    @Scheduled(fixedDelay = 5000L)
    public void processOutboxEvents() {
        domainEventPersistencePort.findAllNotProcessed().forEach(domainEvent -> {
            try {
                kafkaTemplate.send(
                        domainEvent.getEventType(),
                        domainEvent.getAggregateId(),
                        objectMapper.writeValueAsString(domainEvent)
                );
                domainEventPersistencePort.completeProcess(domainEvent);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

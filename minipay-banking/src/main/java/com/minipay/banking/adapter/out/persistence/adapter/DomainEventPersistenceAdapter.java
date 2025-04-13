package com.minipay.banking.adapter.out.persistence.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minipay.banking.adapter.out.persistence.entity.OutboxJpaEntity;
import com.minipay.banking.adapter.out.persistence.repository.SpringDataOutboxRepository;
import com.minipay.banking.application.port.out.DomainEventPersistencePort;
import com.minipay.common.annotation.PersistenceAdapter;
import com.minipay.common.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class DomainEventPersistenceAdapter implements DomainEventPersistencePort {

    private final SpringDataOutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void save(DomainEvent domainEvent) {
        OutboxJpaEntity outboxJpaEntity = OutboxJpaEntity.builder()
                .eventUuid(domainEvent.getEventUuid())
                .serializedEvent(objectMapper.writeValueAsString(domainEvent))
                .published(false)
                .build();

        outboxRepository.save(outboxJpaEntity);
    }

    @Override
    public List<DomainEvent> findAllNotProcessed() {
        return outboxRepository.findAllByPublishedFalseOrderByIdAsc().stream()
                .map(outbox -> {
                    try {
                        return objectMapper.readValue(outbox.getSerializedEvent(), DomainEvent.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    @Override
    public void completeProcess(DomainEvent domainEvent) {
        OutboxJpaEntity outbox = outboxRepository.findByEventUuid(domainEvent.getEventUuid());
        outbox.markAsPublished();

        outboxRepository.save(outbox);
    }
}

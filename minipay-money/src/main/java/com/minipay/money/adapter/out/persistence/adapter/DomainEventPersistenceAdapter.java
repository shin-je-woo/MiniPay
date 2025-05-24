package com.minipay.money.adapter.out.persistence.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minipay.common.annotation.PersistenceAdapter;
import com.minipay.common.event.DomainEvent;
import com.minipay.money.adapter.out.persistence.entity.OutboxJpaEntity;
import com.minipay.money.adapter.out.persistence.repository.SpringDataOutboxRepository;
import com.minipay.money.application.port.out.DomainEventPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
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
                .eventId(domainEvent.getEventUuid())
                .serializedEvent(objectMapper.writeValueAsString(domainEvent))
                .published(false)
                .createdAt(LocalDateTime.now())
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
        OutboxJpaEntity outbox = outboxRepository.findByEventId(domainEvent.getEventUuid());
        outbox.markAsPublished();

        outboxRepository.save(outbox);
    }
}

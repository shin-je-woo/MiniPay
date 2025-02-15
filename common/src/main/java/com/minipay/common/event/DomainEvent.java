package com.minipay.common.event;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(force = true)
public class DomainEvent {

    private final UUID eventUuid;
    private final EventType eventType;
    private final AggregateType aggregateType;
    private final UUID aggregateId;
    private final Object payload;
    private final long timestamp;

    public DomainEvent(EventType eventType, AggregateType aggregateType, UUID aggregateId, Object payload) {
        this.eventUuid = UUID.randomUUID();
        this.eventType = eventType;
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.payload = payload;
        this.timestamp = System.currentTimeMillis();
    }
}

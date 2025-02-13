package com.minipay.common.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DomainEvent {

    private final UUID uuid;
    private final String eventType;
    private final String aggregateType;
    private final String aggregateId;
    private final Object payload;
    private final long timestamp;

    public DomainEvent(String eventType, String aggregateType, String aggregateId, Object payload) {
        this.uuid = UUID.randomUUID();
        this.eventType = eventType;
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.payload = payload;
        this.timestamp = System.currentTimeMillis();
    }
}

package com.minipay.common.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DomainEventHandler {

    private final DomainEventPersistencePort domainEventPersistencePort;

    @Transactional
    @EventListener
    public void handleDomainEvent(DomainEvent event) {
        domainEventPersistencePort.save(event);
    }
}

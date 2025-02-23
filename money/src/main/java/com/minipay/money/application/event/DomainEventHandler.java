package com.minipay.money.application.event;

import com.minipay.common.event.DomainEvent;
import com.minipay.money.application.port.out.DomainEventPersistencePort;
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

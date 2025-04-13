package com.minipay.money.application.event;

import com.minipay.money.application.port.out.DomainEventPersistencePort;
import com.minipay.money.application.port.out.DomainEventPublishPort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DomainEventPublisher {

    private final DomainEventPersistencePort domainEventPersistencePort;
    private final DomainEventPublishPort domainEventPublishPort;

    @Transactional
    @Scheduled(fixedDelay = 1000L)
    public void publishEvent() {
        domainEventPersistencePort.findAllNotProcessed().forEach(domainEvent -> {
            domainEventPublishPort.publish(domainEvent);
            domainEventPersistencePort.completeProcess(domainEvent);
        });
    }
}

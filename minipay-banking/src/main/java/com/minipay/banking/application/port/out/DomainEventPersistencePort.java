package com.minipay.banking.application.port.out;

import com.minipay.common.event.DomainEvent;

import java.util.List;

public interface DomainEventPersistencePort {
    void save(DomainEvent domainEvent);
    List<DomainEvent> findAllNotProcessed();;
    void completeProcess(DomainEvent domainEvent);
}

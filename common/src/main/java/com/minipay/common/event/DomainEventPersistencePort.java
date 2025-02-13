package com.minipay.common.event;

import java.util.List;

public interface DomainEventPersistencePort {
    void save(DomainEvent domainEvent);
    List<DomainEvent> findAllNotProcessed();;
    void completeProcess(DomainEvent domainEvent);
}

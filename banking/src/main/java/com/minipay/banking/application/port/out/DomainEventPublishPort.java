package com.minipay.banking.application.port.out;

import com.minipay.common.event.DomainEvent;

public interface DomainEventPublishPort {
    void publish(DomainEvent domainEvent);
}

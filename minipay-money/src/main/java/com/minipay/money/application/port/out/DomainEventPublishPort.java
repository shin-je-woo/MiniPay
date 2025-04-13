package com.minipay.money.application.port.out;

import com.minipay.common.event.DomainEvent;

public interface DomainEventPublishPort {
    void publish(DomainEvent domainEvent);
}

package com.minipay.common.event;

import org.springframework.context.ApplicationEventPublisher;

public class Events {

    private static ApplicationEventPublisher publisher;

    public static void setPublisher(ApplicationEventPublisher applicationEventPublisher) {
        Events.publisher = applicationEventPublisher;
    }

    public static void raise(DomainEvent event) {
        if (publisher == null) {
            throw new IllegalStateException("ApplicationEventPublisher가 아직 설정되지 않았습니다.");
        }
        publisher.publishEvent(event);
    }
}
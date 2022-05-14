package com.novilabs.brewery.service;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DistributorServiceEventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public DistributorServiceEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Async
    void publishRestockEvent(String distributorId, String upc) {
        // todo replace with JMS message once the monolith is decomposed
        applicationEventPublisher.publishEvent(new DistributorRestockEvent(this, distributorId, upc, 100L));
    }

    void publishEvent(ApplicationEvent applicationEvent) {
        applicationEventPublisher.publishEvent(applicationEvent);
    }
}

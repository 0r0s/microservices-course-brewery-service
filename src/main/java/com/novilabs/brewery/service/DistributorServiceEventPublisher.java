package com.novilabs.brewery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DistributorServiceEventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private KafkaTemplate<String, DistributorRestockEvent> kafkaTemplate;

    public DistributorServiceEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Async
    void publishRestockEvent(String distributorId, String upc) {
        kafkaTemplate.send("distributorRestockTwo", new DistributorRestockEvent(distributorId, upc, 100L));
    }

    public void publishEvent(DistributorTakeStockEvent distributorTakeStockEvent) {

    }

    public void publishEvent(DistributorHasStockEvent distributorHasStockEvent) {
    }
}

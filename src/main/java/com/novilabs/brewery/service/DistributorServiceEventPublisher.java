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
    private KafkaTemplate<String, DistributorRestockEvent> distributorRestockEventKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, DistributorTakeStockEvent> distributorTakeStockEventKafkaTemplate;

    public DistributorServiceEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Async
    void publishRestockEvent(String distributorId, String upc) {
        distributorRestockEventKafkaTemplate.send("distributorRestockTwo", new DistributorRestockEvent(distributorId, upc, 100L));
    }

    void publishEvent(ApplicationEvent applicationEvent) {
        applicationEventPublisher.publishEvent(applicationEvent);
    }

    public void publishTakeStockEvent(DistributorTakeStockEvent distributorTakeStockEvent) {
        distributorTakeStockEventKafkaTemplate.send("distributorTakesStock", distributorTakeStockEvent);
    }
}

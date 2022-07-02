package com.novilabs.brewery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DistributorServiceEventPublisher {

    @Autowired
    private KafkaTemplate<String, DistributorRestockEvent> distributorRestockEventKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, DistributorTakeStockEvent> distributorTakeStockEventKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, DistributorHasStockEvent> distributorHasStockEventKafkaTemplate;

    @Async
    void publishRestockEvent(String distributorId, String upc) {
        distributorRestockEventKafkaTemplate.send("distributorRestockTwo", new DistributorRestockEvent(distributorId, upc, 100L));
    }

    public void publishTakeStockEvent(DistributorTakeStockEvent distributorTakeStockEvent) {
        distributorTakeStockEventKafkaTemplate.send("distributorTakesStock", distributorTakeStockEvent);
    }

    public void publishHasStockEvent(DistributorHasStockEvent distributorHasStockEvent) {
        distributorHasStockEventKafkaTemplate.send("distributorHasNewStockTwo", distributorHasStockEvent);
    }
}

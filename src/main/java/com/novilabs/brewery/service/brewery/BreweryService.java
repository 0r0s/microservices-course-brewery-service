package com.novilabs.brewery.service.brewery;

import com.novilabs.brewery.service.DistributorCannotProvideStockAnymoreEvent;
import com.novilabs.brewery.service.DistributorRestockFulfilledEvent;
import com.novilabs.brewery.service.DistributorRestockEvent;
import com.novilabs.brewery.service.DistributorTakeStockEvent;
import com.novilabs.brewery.web.model.BeerDto;
import com.novilabs.brewery.web.service.BeerInventoryService;
import com.novilabs.brewery.web.service.BeerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

import static java.lang.String.format;

@Slf4j
@Service
public class BreweryService {
    private BeerService beerService;
    private BeerInventoryService beerInventoryService;
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private KafkaTemplate<String, DistributorRestockFulfilledEvent> restockFulfilledKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, DistributorCannotProvideStockAnymoreEvent> restockFailedKafkaTemplate;

    public BreweryService(BeerService beerService, BeerInventoryService beerInventoryService, ApplicationEventPublisher applicationEventPublisher) {
        this.beerService = beerService;
        this.beerInventoryService = beerInventoryService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @KafkaListener(topics = "distributorRestockTwo", groupId = "groupOne", containerFactory = "restockKafkaListenerContainerFactory")
    private void handleDistributorRestockRequest(DistributorRestockEvent event) {
        Random random = new Random();
        DistributorRestockEvent restockEvent = event;
        int nextInt = random.nextInt();
        if (nextInt % 5 != 0) {
            String upc = restockEvent.getUpc();
            BeerDto beerByUpc = beerService.getBeerByUpc(upc);
            if (beerByUpc == null) {
                throw new IllegalArgumentException(format("Beer with upc %s does not exist", upc));
            }
            Long beerStock = beerInventoryService.getBeerStock(upc);
            if (beerStock < restockEvent.getCount()) {
                beerInventoryService.addBeerStock(upc, restockEvent.getCount() - beerStock); // match requested bottles
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                log.info("SleepÏ interrupted.");
            }
            restockFulfilledKafkaTemplate.send("restockEventFulfilledTwo", new DistributorRestockFulfilledEvent(restockEvent.getDistributorId(), restockEvent.getUpc(), restockEvent.getCount()));
        } else {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                log.info("Sleep interrupted.");
            }
            restockFailedKafkaTemplate.send("restockEventNotFulfilled", new DistributorCannotProvideStockAnymoreEvent(restockEvent.getDistributorId(), restockEvent.getUpc(), restockEvent.getCount()));
        }
    }

    @KafkaListener(topics = "distributorTakesStock", groupId = "groupOne", containerFactory = "distributorTakesStockKafkaListenerContainerFactory")
    private void handleDistributorTakesStock(DistributorTakeStockEvent event) {
        DistributorTakeStockEvent takeStockEvent = event;
        String upc = takeStockEvent.getUpc();
        BeerDto beerById = beerService.getBeerByUpc(upc);
        if (beerById == null) {
            throw new IllegalArgumentException(format("Beer with upc %s does not exist", upc));
        }
        beerInventoryService.decreaseBeerStock(upc, takeStockEvent.getCount());
    }
}

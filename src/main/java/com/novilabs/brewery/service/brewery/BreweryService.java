package com.novilabs.brewery.service.brewery;

import com.novilabs.brewery.service.DistributorRestockEventFulfilled;
import com.novilabs.brewery.service.DistributorRestockEvent;
import com.novilabs.brewery.service.DistributorTakeStockEvent;
import com.novilabs.brewery.web.model.BeerDto;
import com.novilabs.brewery.web.service.BeerInventoryService;
import com.novilabs.brewery.web.service.BeerService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class BreweryService implements ApplicationListener<ApplicationEvent> {
    private BeerService beerService;
    private BeerInventoryService beerInventoryService;
    private ApplicationEventPublisher applicationEventPublisher;

    public BreweryService(BeerService beerService, BeerInventoryService beerInventoryService, ApplicationEventPublisher applicationEventPublisher) {
        this.beerService = beerService;
        this.beerInventoryService = beerInventoryService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof DistributorRestockEvent) {
            DistributorRestockEvent restockEvent = (DistributorRestockEvent) event;
            String upc = restockEvent.getUpc();
            BeerDto beerByUpc = beerService.getBeerByUpc(upc);
            if (beerByUpc == null) {
                throw new IllegalArgumentException(format("Beer with upc %s does not exist", upc));
            }
            Long beerStock = beerInventoryService.getBeerStock(upc);
            if (beerStock < restockEvent.getCount()) {
                beerInventoryService.addBeerStock(upc,  restockEvent.getCount() - beerStock); // match requested bottles
            }
            // todo replace with JMS message once the monolith is decomposed
            applicationEventPublisher.publishEvent(new DistributorRestockEventFulfilled(this, restockEvent.getDistributorId(), restockEvent.getUpc(), restockEvent.getCount()));

        } else if (event instanceof DistributorTakeStockEvent) {
            DistributorTakeStockEvent takeStockEvent = (DistributorTakeStockEvent) event;
            String upc = takeStockEvent.getUpc();
            BeerDto beerById = beerService.getBeerByUpc(upc);
            if (beerById == null) {
                throw new IllegalArgumentException(format("Beer with upc %s does not exist", upc));
            }
            beerInventoryService.decreaseBeerStock(upc, takeStockEvent.getCount());
        }
    }
}

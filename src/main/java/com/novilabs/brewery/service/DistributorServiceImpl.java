package com.novilabs.brewery.service;

import com.novilabs.brewery.web.model.Brewery;
import com.novilabs.brewery.web.model.DistributorDto;
import com.novilabs.brewery.web.service.DistributorService;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DistributorServiceImpl implements DistributorService {

    private final ConcurrentHashMap <String, DistributorDto> allDistributors = new ConcurrentHashMap<>();

    private DistributorServiceEventPublisher eventPublisher;

    public DistributorServiceImpl(DistributorServiceEventPublisher publisher) {
        this.eventPublisher = publisher;
        DistributorDto value = new DistributorDto();
        value.setId(UUID.randomUUID().toString());
        HashMap<String, Long> beerStock = new HashMap<>();
        beerStock.put("666666", 100L);
        value.setBeerStock(beerStock);
        HashMap<String, Brewery> beerProducers = new HashMap<>();
        beerProducers.put("666666", new Brewery());
        value.setBeerProducers(beerProducers);
        allDistributors.put("666666", value);
    }

    @Override
    public void addBeerStock(String distributorId, String upc, Long count) {
        DistributorDto distributorDto = getDistributorDto(distributorId);
        if (distributorDto == null) {
            throw new IllegalArgumentException(("Distributor id is invalid"));
        }
        HashMap<String, Long> beerStock = distributorDto.getBeerStock();
        Long beerStockValue = beerStock.get(upc) + count;
        beerStock.put(upc, beerStockValue);
    }

    @Override
    public Long getNewBeersFromStock(String distributorId, String upc, Long count) {
        DistributorDto distributorDto = getDistributorDto(distributorId);
        if (distributorDto == null) {
            throw new IllegalArgumentException(("Distributor id is invalid"));
        }
        HashMap<String, Long> beerStock = distributorDto.getBeerStock();
        Long stockForBeer = beerStock.get(upc);
        long result;
        if (stockForBeer > count) {
            // enough is available in distributor stock
            Long remainingInStock = stockForBeer - count;
            beerStock.put(upc, remainingInStock);
            result = 0L;
        } else {
            // not enough is available in distributor stock
            long remainingToOrder = count - stockForBeer;
            beerStock.put(upc, 0L);
            result = remainingToOrder;
            eventPublisher.publishRestockEvent(distributorId, upc);
        }
        return result;
    }

    private DistributorDto getDistributorDto(String distributorId) {
        for (String s : allDistributors.keySet()) {
            if (allDistributors. get(s).getId().equals(distributorId)) {
                return allDistributors.get(s);
            }
        }
        return allDistributors.get(distributorId);
    }

    @Override
    public Map<String, DistributorDto> getAllDistributors() {
        return allDistributors;
    }


    @KafkaListener(topics = "restockEventFulfilledTwo", groupId = "groupOne", containerFactory = "restockFulfilledKafkaListenerContainerFactory")
    public void onApplicationEvent(DistributorRestockFulfilledEvent event) {
        eventPublisher.publishEvent(new DistributorTakeStockEvent(this, event.getBeerId(), event.getCount()));
        String upc = getUpcFromBeerId(event.getBeerId());
        addBeerStock(event.getDistributorId(), upc, event.getCount());
        eventPublisher.publishEvent(new DistributorHasStockEvent(this, event.getDistributorId(), event.getBeerId(), event.getCount()));
    }

    private String getUpcFromBeerId(String beerId) {
        // todo get valid value
        return "666666";
    }
}

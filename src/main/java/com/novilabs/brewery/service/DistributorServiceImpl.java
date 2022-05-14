package com.novilabs.brewery.service;

import com.novilabs.brewery.web.model.Brewery;
import com.novilabs.brewery.web.model.DistributorDto;
import com.novilabs.brewery.web.service.DistributorService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class DistributorServiceImpl implements DistributorService, ApplicationListener<DistributorRestockEventFulfilled> {

    private final ConcurrentHashMap <String, DistributorDto> allDistributors = new ConcurrentHashMap<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private ApplicationEventPublisher applicationEventPublisher;

    public DistributorServiceImpl(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
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
            // todo replace with JMS message once the monolith is decomposed
            applicationEventPublisher.publishEvent(new DistributorRestockEvent(this, distributorId, upc, 100L));
        }
        return result;
    }

    private DistributorDto getDistributorDto(String distributorId) {
        for (String s : allDistributors.keySet()) {
            if (allDistributors.get(s).getId().equals(distributorId)) {
                return allDistributors.get(s);
            }
        }
        return allDistributors.get(distributorId);
    }

    @Override
    public Map<String, DistributorDto> getAllDistributors() {
        return allDistributors;
    }

    @Override
    public void onApplicationEvent(DistributorRestockEventFulfilled event) {
        applicationEventPublisher.publishEvent(new DistributorTakeStockEvent(this, event.getBeerId(), event.getCount()));
        String upc = getUpcFromBeerId(event.getBeerId());
        addBeerStock(event.getDistributorId(), upc, event.getCount());
    }

    private String getUpcFromBeerId(String beerId) {
        // todo get valid value
        return "666666";
    }
}

package com.novilabs.brewery.service.brewery;

import com.novilabs.brewery.web.service.BeerInventoryService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class BeerInventoryServiceImpl implements BeerInventoryService {
    private ConcurrentHashMap<String, Long> stock;

    @Override
    public void addBeerStock(String uuid, Long count) {
        stock.compute(uuid, (id, beerCount) -> {
            if (beerCount != null) {
                return count + beerCount;
            }
            return 1L;
        });
    }

    @Override
    public void decreaseBeerStock(String uuid, Long count) {
        stock.compute(uuid, (id, beerCount) -> {
            if (beerCount != null && beerCount > count) {
                return beerCount - count;
            }
            return 0L;
        });
    }
}

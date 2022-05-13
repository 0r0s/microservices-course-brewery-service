package com.novilabs.brewery.web.service;

public interface BeerInventoryService {
    void addBeerStock(String uuid, Long count);
    void decreaseBeerStock(String uuid, Long count);
}

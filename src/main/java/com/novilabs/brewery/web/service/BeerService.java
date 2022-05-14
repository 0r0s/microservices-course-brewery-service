package com.novilabs.brewery.web.service;

import com.novilabs.brewery.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID uuid);

    BeerDto getBeerByUpc(String upc);

    BeerDto deleteBeer(UUID uuid);

    BeerDto createBeer(BeerDto beerDto);

    void updateBeer(UUID uuid, BeerDto beerDto);
}

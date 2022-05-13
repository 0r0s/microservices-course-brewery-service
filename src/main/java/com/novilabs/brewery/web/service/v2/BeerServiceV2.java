package com.novilabs.brewery.web.service.v2;

import com.novilabs.brewery.web.model.v2.BeerDtoV2;

import java.util.UUID;

public interface BeerServiceV2 {
    BeerDtoV2 getBeerById(UUID uuid);

    BeerDtoV2 deleteBeer(UUID uuid);

    BeerDtoV2 createBeer(BeerDtoV2 beerDto);

    void updateBeer(UUID uuid, BeerDtoV2 beerDto);
}

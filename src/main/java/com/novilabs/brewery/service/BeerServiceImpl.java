package com.novilabs.brewery.service;

import com.novilabs.brewery.web.model.BeerDto;
import com.novilabs.brewery.web.service.BeerService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {

    private static HashMap<String, BeerDto> data = new HashMap<>();

    @Override
    public BeerDto getBeerById(UUID uuid) {
        return data.get(uuid.toString());
    }

    @Override
    public BeerDto deleteBeer(UUID uuid) {
        return data.remove(uuid.toString());
    }

    @Override
    public BeerDto createBeer(BeerDto beerDto) {
        UUID uuid = UUID.randomUUID();
        beerDto.setId(uuid);
        data.put(uuid.toString(), beerDto);
        return beerDto;
    }

    @Override
    public void updateBeer(UUID uuid, BeerDto beerDto) {
        beerDto.setId(uuid);
        data.put(uuid.toString(), beerDto);
    }
}

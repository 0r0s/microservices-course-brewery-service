package com.novilabs.brewery.service.v2;

import com.novilabs.brewery.web.model.v2.BeerDtoV2;
import com.novilabs.brewery.web.service.v2.BeerServiceV2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class BeerServiceImplV2 implements BeerServiceV2 {

    private static HashMap<String, BeerDtoV2> data = new HashMap<>();

    @Override
    public BeerDtoV2 getBeerById(UUID uuid) {
        return data.get(uuid.toString());
    }

    @Override
    public BeerDtoV2 deleteBeer(UUID uuid) {
        return data.remove(uuid.toString());
    }

    @Override
    public BeerDtoV2 createBeer(BeerDtoV2 beerDto) {
        UUID uuid = UUID.randomUUID();
        beerDto.setId(uuid);
        data.put(uuid.toString(), beerDto);
        return beerDto;
    }

    @Override
    public void updateBeer(UUID uuid, BeerDtoV2 beerDto) {
        beerDto.setId(uuid);
        data.put(uuid.toString(), beerDto);
    }
}

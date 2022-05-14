package com.novilabs.brewery.service.brewery;

import com.novilabs.brewery.web.model.BeerDto;
import com.novilabs.brewery.web.service.BeerInventoryService;
import com.novilabs.brewery.web.service.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private static HashMap<String, BeerDto> beerTypes = new HashMap<>();
    private BeerInventoryService beerInventoryService;

    @Override
    public BeerDto getBeerById(UUID uuid) {
        return beerTypes.get(uuid.toString());
    }

    @Override
    public BeerDto getBeerByUpc(String upc) {
        for (Map.Entry<String, BeerDto> stringBeerDtoEntry : beerTypes.entrySet()) {
            if (stringBeerDtoEntry.getValue().getUpc().equals(upc)) {
                return stringBeerDtoEntry.getValue();
            }
        }
        return null;
    }

    @Override
    public BeerDto deleteBeer(UUID uuid) {
        return beerTypes.remove(uuid.toString());
    }

    @Override
    public BeerDto createBeer(BeerDto beerDto) {
        UUID uuid = UUID.randomUUID();
        beerDto.setId(uuid);
        beerTypes.put(uuid.toString(), beerDto);
        return beerDto;
    }

    @Override
    public void updateBeer(UUID uuid, BeerDto beerDto) {
        beerDto.setId(uuid);
        beerTypes.put(uuid.toString(), beerDto);
    }
}

package com.novilabs.brewery.service;

import com.novilabs.brewery.web.model.BeerDto;
import com.novilabs.brewery.web.service.BeerService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDto getBeerById(UUID uuid) {
        return BeerDto.builder()
                .id(uuid)
                .name("Timisoreana")
                .style("Bere clasica")
                .upc("999-111").build();
    }
}

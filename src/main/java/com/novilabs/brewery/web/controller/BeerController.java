package com.novilabs.brewery.web.controller;

import com.novilabs.brewery.web.service.BeerService;
import com.novilabs.brewery.web.model.BeerDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/beers")
@RestController
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{uuid}")
    public BeerDto getBeer(@PathVariable UUID uuid) {
        return beerService.getBeerById(uuid);
    }
}

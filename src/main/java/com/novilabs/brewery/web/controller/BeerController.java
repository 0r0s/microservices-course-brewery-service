package com.novilabs.brewery.web.controller;

import com.novilabs.brewery.web.model.BeerDto;
import com.novilabs.brewery.web.service.BeerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RequestMapping("/api/v1/beers")
@RestController
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<BeerDto> getBeer(@PathVariable UUID uuid) {
        BeerDto dto = beerService.getBeerById(uuid);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<BeerDto> createBeer(@RequestBody BeerDto beerDto) throws URISyntaxException {
        BeerDto dto = beerService.createBeer(beerDto);
        return ResponseEntity.created(new URI("/api/v1/beers/" + dto.getId())).build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<BeerDto> updateBeer(@PathVariable UUID uuid, @RequestBody BeerDto beerDto) {
        beerService.updateBeer(uuid, beerDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<BeerDto> deleteBeer(@PathVariable UUID uuid) {
        BeerDto dto = beerService.deleteBeer(uuid);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}

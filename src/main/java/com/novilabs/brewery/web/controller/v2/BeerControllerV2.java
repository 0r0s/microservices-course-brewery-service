package com.novilabs.brewery.web.controller.v2;

import com.novilabs.brewery.web.model.v2.BeerDtoV2;
import com.novilabs.brewery.web.service.v2.BeerServiceV2;
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

@RequestMapping("/api/v2/beers")
@RestController
public class BeerControllerV2 {

    private final BeerServiceV2 beerService;

    public BeerControllerV2(BeerServiceV2 beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<BeerDtoV2> getBeer(@PathVariable UUID uuid) {
        BeerDtoV2 dto = beerService.getBeerById(uuid);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<BeerDtoV2> createBeer(@RequestBody BeerDtoV2 beerDto) throws URISyntaxException {
        BeerDtoV2 dto = beerService.createBeer(beerDto);
        return ResponseEntity.created(new URI("/beers/" + dto.getId())).build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<BeerDtoV2> updateBeer(@PathVariable UUID uuid, @RequestBody BeerDtoV2 beerDto) {
        beerService.updateBeer(uuid, beerDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<BeerDtoV2> deleteBeer(@PathVariable UUID uuid) {
        BeerDtoV2 dto = beerService.deleteBeer(uuid);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}

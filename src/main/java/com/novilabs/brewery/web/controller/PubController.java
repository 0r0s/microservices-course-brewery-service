package com.novilabs.brewery.web.controller;

import com.novilabs.brewery.service.PubServiceImpl;
import com.novilabs.brewery.web.model.PubDTO;
import com.novilabs.brewery.web.model.pub.Restock;
import com.novilabs.brewery.web.model.pub.SellBeerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RequestMapping("/api/pub")
@RestController
public class PubController {

    private PubServiceImpl pubService;

    public PubController(PubServiceImpl pubService) {
        this.pubService = pubService;
    }

    @PostMapping("/sell")
    public ResponseEntity<Long> sellBeerToCustomer(@RequestBody SellBeerDto sellBeerDto) {
        Long difference = pubService.sellBeerToCustomer(sellBeerDto.getPubId(), sellBeerDto.getBeerId(), sellBeerDto.getCustomer(), sellBeerDto.getCount());
        return ResponseEntity.ok(difference);
    }

    @PostMapping("/restock")
    public ResponseEntity<Long> restock(@RequestBody Restock restock) {
        Long difference = pubService.addBeerStock(restock.getPubId(), restock.getBeerId(), restock.getCount());
        return ResponseEntity.ok(difference);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody String name) throws URISyntaxException {
        PubDTO dto = pubService.createPub(name);
        return ResponseEntity.created(new URI("/api/v1/pubs/" + dto.getId())).build();
    }
}

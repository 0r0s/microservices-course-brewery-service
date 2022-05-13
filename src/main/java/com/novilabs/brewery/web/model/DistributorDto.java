package com.novilabs.brewery.web.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class DistributorDto {
    private String id;
    private String name;
    private HashMap<String, Brewery> beerProducers;
    private HashMap<String, Long> beerStock;
}

package com.novilabs.brewery.web.model;

import com.novilabs.brewery.web.model.pub.Order;
import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class PubDTO {
    private  String id;
    private  String name;
    private ConcurrentHashMap<String, Long> pubStock= new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, DistributorDto> distributors = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Order> orders = new ConcurrentHashMap<>();
}

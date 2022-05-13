package com.novilabs.brewery.web.service;

import com.novilabs.brewery.web.model.CustomerDto;

public interface PubService {
    Long addBeerStock(String uuid, String beerId, Long count);
    Long sellBeerToCustomer(String pubId, String beerId, CustomerDto customerDto, Long count);
}

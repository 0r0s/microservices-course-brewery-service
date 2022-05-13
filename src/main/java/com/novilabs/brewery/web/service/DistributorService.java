package com.novilabs.brewery.web.service;


import com.novilabs.brewery.web.model.DistributorDto;

import java.util.Map;

public interface DistributorService {
    void addBeerStock(String distributorId, String beerId, Long count);
    Long getNewBeersFromStock(String distributorId, String beerId, Long count);
    Map<String, DistributorDto> getAllDistributors();
}

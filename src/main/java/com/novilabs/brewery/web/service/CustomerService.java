package com.novilabs.brewery.web.service;

import com.novilabs.brewery.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {
    CustomerDto getCustomerById(UUID uuid);
}

package com.novilabs.brewery.web.service;

import com.novilabs.brewery.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {
    CustomerDto getCustomerById(UUID uuid);

    CustomerDto createCustomer(CustomerDto customerDto);

    void updateCustomer(UUID uuid, CustomerDto customerDto);

    CustomerDto deleteCustomer(UUID uuid);
}

package com.novilabs.brewery.web.controller;

import com.novilabs.brewery.web.service.CustomerService;
import com.novilabs.brewery.web.model.CustomerDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/customers")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{uuid}")
    public CustomerDto getCustomerById(@PathVariable UUID uuid) {
        return customerService.getCustomerById(uuid);
    }
}

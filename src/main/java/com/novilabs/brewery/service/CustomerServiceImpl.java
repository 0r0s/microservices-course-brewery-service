package com.novilabs.brewery.service;

import com.novilabs.brewery.web.model.CustomerDto;
import com.novilabs.brewery.web.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl  implements CustomerService {

    @Override
    public CustomerDto getCustomerById(UUID uuid) {
        return CustomerDto.builder()
                .id(uuid)
                .name("Jim")
                .build();
    }
}

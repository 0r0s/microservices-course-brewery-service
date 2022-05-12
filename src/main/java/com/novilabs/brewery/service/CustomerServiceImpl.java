package com.novilabs.brewery.service;

import com.novilabs.brewery.web.model.CustomerDto;
import com.novilabs.brewery.web.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class CustomerServiceImpl  implements CustomerService {

    private static HashMap<String, CustomerDto> data = new HashMap<>();

    @Override
    public CustomerDto getCustomerById(UUID uuid) {
       return data.get(uuid.toString());
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        UUID uuid = UUID.randomUUID();
        customerDto.setId(uuid);
        data.put(uuid.toString(), customerDto);
        return customerDto;
    }

    @Override
    public void updateCustomer(UUID uuid, CustomerDto customerDto) {
        customerDto.setId(uuid);
        data.put(uuid.toString(), customerDto);
    }

    @Override
    public CustomerDto deleteCustomer(UUID uuid) {
        return data.remove(uuid.toString());
    }
}

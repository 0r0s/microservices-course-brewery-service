package com.novilabs.brewery.web.controller;

import com.novilabs.brewery.web.model.CustomerDto;
import com.novilabs.brewery.web.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RequestMapping("/customers")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable UUID uuid) {
        CustomerDto customerById = customerService.getCustomerById(uuid);
        if (customerById == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerById);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) throws URISyntaxException {
      CustomerDto savedCustomerDto = customerService.createCustomer(customerDto);
      return ResponseEntity.created(new URI("/customers/" + savedCustomerDto.getId())).build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable UUID uuid, @RequestBody CustomerDto customerDto) {
        customerService.updateCustomer(uuid, customerDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<CustomerDto> deleteCustomer(@PathVariable UUID uuid) {
        CustomerDto dto = customerService.deleteCustomer(uuid);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}

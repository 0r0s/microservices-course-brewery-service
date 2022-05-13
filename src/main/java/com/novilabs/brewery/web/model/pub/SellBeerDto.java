package com.novilabs.brewery.web.model.pub;

import com.novilabs.brewery.web.model.CustomerDto;
import lombok.Data;

@Data
public class SellBeerDto {
    private String pubId;
    private String beerId;
    private CustomerDto customer;
    private Long count;
}

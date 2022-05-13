package com.novilabs.brewery.web.model.v2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BeerDtoV2 {
    private UUID id;
    private String name;
    private BeerStyle style;
    private String upc;
}

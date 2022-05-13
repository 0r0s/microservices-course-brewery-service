package com.novilabs.integration.beers;

import com.novilabs.brewery.web.model.BeerDto;
import com.novilabs.integration.AbstractIntegrationTest;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UpdateBeerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void testUpdateBeer() {
        BeerDto dto = new BeerDto();
        dto.setName("My bear");
        dto.setStyle("IPA");
        dto.setUpc("444-5555");
        testRestTemplate.postForEntity(absUrl("/api/v1/beers"), dto, BeerDto.class);
        ResponseEntity<BeerDto> response = testRestTemplate.postForEntity(absUrl("/api/v1/beers"), dto, BeerDto.class);
        String path = response.getHeaders().getLocation().getPath();
        dto.setName("My cool name");

        testRestTemplate.put(absUrl(path), dto);

        ResponseEntity<BeerDto> createdBeerResponse = testRestTemplate.getForEntity(absUrl(path), BeerDto.class);
        RecursiveComparisonConfiguration config = new RecursiveComparisonConfiguration();
        config.ignoreFields("id");
        assertThat(createdBeerResponse.getBody()).isNotNull();
        assertThat(createdBeerResponse.getBody()).usingRecursiveComparison(config).isEqualTo(dto);
    }
}

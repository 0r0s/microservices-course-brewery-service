package com.novilabs.integration.beers;

import com.novilabs.brewery.web.model.BeerDto;
import com.novilabs.integration.AbstractIntegrationTest;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GetBeerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void testGetBeer() {
        BeerDto dto = new BeerDto();
        dto.setName("My bear");
        dto.setStyle("IPA");
        dto.setUpc("444-5555");
        ResponseEntity<BeerDto> response = testRestTemplate.postForEntity(absUrl("/api/v1/beers"), dto, BeerDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String path = response.getHeaders().getLocation().getPath();

        ResponseEntity<BeerDto> createdBeerResponse = testRestTemplate.getForEntity(absUrl(path), BeerDto.class);

        assertThat(createdBeerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        BeerDto body = createdBeerResponse.getBody();
        assertThat(body).isNotNull();
        RecursiveComparisonConfiguration config = new RecursiveComparisonConfiguration();
        config.ignoreFields("id");
        assertThat(body).usingRecursiveComparison(config).isEqualTo(dto);
    }
}

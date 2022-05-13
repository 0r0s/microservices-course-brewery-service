package com.novilabs.integration.beers;

import com.novilabs.brewery.web.model.BeerDto;
import com.novilabs.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CreateBeerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void testCreateBeer() {
        BeerDto dto = new BeerDto();
        dto.setName("My bear");
        dto.setStyle("IPA");
        dto.setUpc("444-5555");

        ResponseEntity<BeerDto> response = testRestTemplate.postForEntity(absUrl("/api/v1/beers"), dto, BeerDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation().getPath()).startsWith("/api/v1/beers/");
    }
}

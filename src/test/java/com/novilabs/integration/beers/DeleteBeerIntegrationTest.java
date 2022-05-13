package com.novilabs.integration.beers;

import com.novilabs.brewery.web.model.BeerDto;
import com.novilabs.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DeleteBeerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void testCreateBeer() {
        BeerDto dto = new BeerDto();
        dto.setName("My bear");
        dto.setStyle("IPA");
        dto.setUpc("444-5555");
        ResponseEntity<BeerDto> responseEntity = testRestTemplate.postForEntity(absUrl("/api/v1/beers"), dto, BeerDto.class);
        String path = responseEntity.getHeaders().getLocation().getPath();

        testRestTemplate.delete(absUrl(path));

        ResponseEntity<BeerDto> getBeerResponse = testRestTemplate.getForEntity(absUrl(path), BeerDto.class);
        assertThat(getBeerResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getBeerResponse.getBody()).isNull();
    }
}

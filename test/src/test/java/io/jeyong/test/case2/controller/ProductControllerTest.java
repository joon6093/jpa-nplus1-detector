package io.jeyong.test.case2.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * @formatter:off
     * Product and Order have a many-to-one (N:1) relationship.
     * This test verifies if an N+1 issue occurs when accessing the /api/products endpoint,
     * which fetches all Products and triggers separate queries to fetch the associated Order for each Product.
     * @formatter:on
     */
    @Test
    void testGetProducts(CapturedOutput output) {
        String url = "http://localhost:" + port + "/api/products";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(output).contains("N+1 issue detected");
    }
}

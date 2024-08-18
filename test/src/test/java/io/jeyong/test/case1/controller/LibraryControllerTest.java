package io.jeyong.test.case1.controller;

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
class LibraryControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * @formatter:off
     * V1 Controller: Test if N+1 issue occurs
     * The N+1 issue should be detected at the /api/library/v1/authors endpoint.
     * @formatter:on
     */
    @Test
    void testGetAuthorsV1(CapturedOutput output) {
        String url = "http://localhost:" + port + "/api/library/v1/authors";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(output).contains("N+1 issue detected");
    }

    /**
     * @formatter:off
     * V2 Controller: Test if N+1 issue occurs
     * The N+1 issue should be detected at the /api/library/v2/authors endpoint.
     * @formatter:on
     */
    @Test
    void testGetAuthorsV2(CapturedOutput output) {
        String url = "http://localhost:" + port + "/api/library/v2/authors";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(output).contains("N+1 issue detected");
    }

    /**
     * @formatter:off
     * V3 Controller: Test if N+1 issue occurs
     * The N+1 issue should be detected at the /api/library/v3/authors endpoint using OSIV.
     * @formatter:on
     */
    @Test
    void testGetAuthorsV3(CapturedOutput output) {
        String url = "http://localhost:" + port + "/api/library/v3/authors";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(output).contains("N+1 issue detected");
    }
}

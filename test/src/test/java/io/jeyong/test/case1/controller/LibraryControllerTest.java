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
     * V1 Controller: N+1 문제가 발생하는지 테스트
     * /api/library/v1/authors 엔드포인트에서 N+1 문제가 감지되어야 합니다.
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
     * V2 Controller: N+1 문제가 발생하는지 테스트
     * /api/library/v2/authors 엔드포인트에서 N+1 문제가 감지되어야 합니다.
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
     * V3 Controller: N+1 문제가 발생하는지 테스트
     * /api/library/v3/authors 엔드포인트에서 OSIV를 사용하여 N+1 문제가 감지되어야 합니다.
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

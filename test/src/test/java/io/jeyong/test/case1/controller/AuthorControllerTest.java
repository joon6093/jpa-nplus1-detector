package io.jeyong.test.case1.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
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
class AuthorControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("클래스 단위의 @Transactional 상황에서 감지한다.")
    void testGetAuthorsV1(CapturedOutput output) {
        String url = "http://localhost:" + port + "/api/v1/authors";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(output).contains("N+1 issue detected");
    }

    @Test
    @DisplayName("메서드 단위의 @Transactional 상황에서 감지한다.")
    void testGetAuthorsV2(CapturedOutput output) {
        String url = "http://localhost:" + port + "/api/v2/authors";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(output).contains("N+1 issue detected");
    }

    @Test
    @DisplayName("OSIV를 이용한 지연 조회 상황에서 감지한다.")
    void testGetAuthorsV3(CapturedOutput output) {
        String url = "http://localhost:" + port + "/api/v3/authors";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(output).contains("N+1 issue detected");
    }
}
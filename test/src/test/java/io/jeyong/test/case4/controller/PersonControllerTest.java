package io.jeyong.test.case4.controller;

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
class PersonControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("1:1 관계에서 일(1)을 조회하는 상황에서 감지한다.")
    void testGetPersons(CapturedOutput output) {
        String url = "http://localhost:" + port + "/api/persons";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(output).contains("N+1 issue detected");
    }
}

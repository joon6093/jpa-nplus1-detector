package io.jeyong.test.level;

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
@SpringBootTest(
        webEnvironment = RANDOM_PORT,
        properties = {
                "logging.level.io.jeyong=debug",
                "spring.jpa.properties.hibernate.detector.level=debug"
        })
class LoggerLevelDebugTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("DEBUG 레벨에서 로그가 출력된다.")
    void testDebugLogLevel(CapturedOutput output) {
        String url = "http://localhost:" + port + "/api/products";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(output).contains("N+1 issue detected");
    }
}

package io.jeyong.test.environment.test.exclude;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.jeyong.core.annotation.NPlusOneTest;
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

@NPlusOneTest(
        exclude = {
                "select b1_0.author_id,b1_0.id,b1_0.title from book b1_0 where b1_0.author_id=?",
                "select o1_0.id,o1_0.order_number from \"order\" o1_0 where o1_0.id=?"
        })
@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ExcludeQueryTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("첫 번째로 제외된 쿼리는 감지하지 않는다.")
    void testFirstExcludedQuery(CapturedOutput output) {
        String url = "http://localhost:" + port + "/api/v1/authors";
        ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(output).doesNotContain("N+1 query detected");
    }

    @Test
    @DisplayName("두 번째로 제외된 쿼리는 감지하지 않는다.")
    void testSecondExcludedQuery(CapturedOutput output) {
        String url = "http://localhost:" + port + "/api/products";
        ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(output).doesNotContain("N+1 query detected");
    }

    @Test
    @DisplayName("제외되지 않은 쿼리는 감지한다.")
    void testNonExcludedQuery(CapturedOutput output) {
        String url = "http://localhost:" + port + "/api/addresses";
        ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(output).contains("N+1 query detected");
    }
}

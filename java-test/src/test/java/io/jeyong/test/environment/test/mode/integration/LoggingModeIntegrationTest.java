package io.jeyong.test.environment.test.mode.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.jeyong.test.annotation.NPlusOneTest;
import io.jeyong.test.case2.service.ProductService;
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

@NPlusOneTest(mode = NPlusOneTest.Mode.LOGGING)
@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class LoggingModeIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("@SpringBootTest를 이용한 API를 호출하는 상황에서 LOGGING 모드가 동작한다.")
    void testLoggingModeInApiCall(CapturedOutput output) {
        String url = "http://localhost:" + port + "/api/products";
        ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(output).contains("N+1 query detected");
    }

    @Test
    @DisplayName("@SpringBootTest를 이용한 Business Logic을 호출에서 상황에서 LOGGING 모드가 동작한다.")
    void testLoggingModeInBusinessLogicCall(CapturedOutput output) {
        productService.findAllProducts();

        assertThat(output).contains("N+1 query detected");
    }
}

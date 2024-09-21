package io.jeyong.test.mode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.jeyong.detector.annotation.NPlusOneTest;
import io.jeyong.detector.config.NPlusOneDetectorProperties;
import io.jeyong.detector.template.NPlusOneQueryLogger;
import io.jeyong.detector.template.NPlusOneQueryTemplate;
import io.jeyong.test.case2.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

@NPlusOneTest(mode = NPlusOneTest.Mode.LOGGING, threshold = 3, level = Level.DEBUG)
@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(
        webEnvironment = RANDOM_PORT,
        properties = {
                "logging.level.io.jeyong=debug"
        })
@TestPropertySource(
        properties = {
                "spring.jpa.properties.hibernate.detector.enabled=false",
                "spring.jpa.properties.hibernate.detector.threshold=10",
                "spring.jpa.properties.hibernate.detector.level=warn"
        })
class AnnotationLoggingModeTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private NPlusOneDetectorProperties nPlusOneDetectorProperties;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("LOGGING 모드의 설정이 우선적으로 적용된다.")
    void testLoggingModeConfiguration() {
        assertThat(nPlusOneDetectorProperties.isEnabled()).isTrue();
        assertThat(nPlusOneDetectorProperties.getThreshold()).isEqualTo(3);
        assertThat(nPlusOneDetectorProperties.getLevel()).isEqualTo(Level.DEBUG);

        NPlusOneQueryTemplate template = applicationContext.getBean(NPlusOneQueryTemplate.class);
        assertThat(template).isInstanceOf(NPlusOneQueryLogger.class);
    }

    @Test
    @DisplayName("API 호출에서 LOGGING 모드가 동작한다.")
    void testLoggingModeInApiCall(CapturedOutput output) {
        String url = "http://localhost:" + port + "/api/products";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(output).contains("N+1 issue detected");
    }

    @Test
    @DisplayName("Business Logic 호출에서 LOGGING 모드가 동작한다.")
    void testLoggingModeInBusinessLogicCall(CapturedOutput output) {
        productService.findAllProducts();

        assertThat(output).contains("N+1 issue detected");
    }
}

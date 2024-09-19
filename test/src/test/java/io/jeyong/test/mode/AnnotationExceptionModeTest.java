package io.jeyong.test.mode;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.jeyong.detector.annotation.NPlusOneTest;
import io.jeyong.detector.config.NPlusOneDetectorProperties;
import io.jeyong.detector.template.NPlusOneQueryCollector;
import io.jeyong.detector.template.NPlusOneQueryTemplate;
import io.jeyong.test.case2.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
// @formatter:off
/**
 * <p>
 * 이 클래스는 {@code @NPlusOneTest} 어노테이션을 {@code EXCEPTION} 모드로 사용했을 때의 동작을 테스트합니다.
 * </p>
 *
 * <p>
 * {@code EXCEPTION} 모드에서는 N+1 쿼리 문제가 감지되면 테스트 메서드가 끝난 후 {@code afterEach}에서 예외가 발생합니다.
 * 따라서 테스트 코드 내에서 예외를 잡아 직접 검증하는 것은 어렵습니다.
 * </p>
 *
 * <p>
 * 예외가 발생하는지 확인하려면 {@code threshold} 값을 낮게 설정(예: 2)하면 됩니다.
 * 예를 들어, {@code threshold}를 2로 설정하면 동일한 쿼리가 2번 이상 실행될 때 {@code NPlusOneQueryException}이 발생하게 됩니다.
 * </p>
 *
 * <p>
 * 예시:
 * </p>
 * <pre>
 * {@code
 * @NPlusOneTest(threshold = 2, mode = NPlusOneTest.Mode.EXCEPTION)
 * }
 * </pre>
 */
// @formatter:on
@NPlusOneTest(threshold = 5, mode = NPlusOneTest.Mode.EXCEPTION)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(
        properties = {
                "spring.jpa.properties.hibernate.detector.enabled=true",
                "spring.jpa.properties.hibernate.detector.threshold=10",
        })
class AnnotationExceptionModeTest {

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
    @DisplayName("EXCEPTION 모드의 설정이 우선적으로 적용된다.")
    void testExceptionModeConfiguration() {
        assertThat(nPlusOneDetectorProperties.isEnabled()).isFalse();
        assertThat(nPlusOneDetectorProperties.getThreshold()).isEqualTo(5);

        NPlusOneQueryTemplate template = applicationContext.getBean(NPlusOneQueryTemplate.class);
        assertThat(template).isInstanceOf(NPlusOneQueryCollector.class);
    }

    @Test
    @DisplayName("API 호출에서 EXCEPTION 모드가 동작한다.")
    void testExceptionModeInApiCall() {
        String url = "http://localhost:" + port + "/api/products";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    @DisplayName("Business Logic 호출에서 EXCEPTION 모드가 동작한다.")
    void testExceptionModeInBusinessLogicCall() {
        productService.findAllProducts();
    }
}

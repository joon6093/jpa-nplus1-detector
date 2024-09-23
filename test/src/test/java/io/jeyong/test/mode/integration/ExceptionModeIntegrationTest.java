package io.jeyong.test.mode.integration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.jeyong.detector.annotation.NPlusOneTest;
import io.jeyong.detector.config.NPlusOneDetectorProperties;
import io.jeyong.detector.template.NPlusOneQueryCollector;
import io.jeyong.detector.template.NPlusOneQueryTemplate;
import io.jeyong.test.case2.service.ProductService;
import io.jeyong.test.case4.service.AddressService;
import io.jeyong.test.case4.service.PersonService;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

@NPlusOneTest(mode = NPlusOneTest.Mode.EXCEPTION, threshold = 3)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(
        properties = {
                "spring.jpa.properties.hibernate.detector.enabled=true",
                "spring.jpa.properties.hibernate.detector.threshold=10",
        })
class ExceptionModeIntegrationTest {

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

    @Autowired
    private AddressService addressService;

    @Autowired
    private PersonService personService;

    @Test
    @DisplayName("EXCEPTION 모드의 설정이 우선적으로 적용된다.")
    void testExceptionModeConfiguration() {
        assertThat(nPlusOneDetectorProperties.isEnabled()).isFalse();
        assertThat(nPlusOneDetectorProperties.getThreshold()).isEqualTo(3);

        NPlusOneQueryTemplate template = applicationContext.getBean(NPlusOneQueryTemplate.class);
        assertThat(template).isInstanceOf(NPlusOneQueryCollector.class);
    }

    @Disabled
    @Test
    @DisplayName("API 호출에서 EXCEPTION 모드가 동작한다.")
    void testExceptionModeInApiCall() {
        String url = "http://localhost:" + port + "/api/products";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Disabled
    @Test
    @DisplayName("Business Logic 호출에서 EXCEPTION 모드가 동작한다.")
    void testExceptionModeInBusinessLogicCall() {
        productService.findAllProducts();
    }

    @Disabled
    @Test
    @DisplayName("여러번의 예외에서 EXCEPTION 모드가 동작한다.")
    void testExceptionModeInMultipleExceptions() {
        productService.findAllProducts();
        addressService.findAllAddresses();
        personService.findAllPersons();
    }
}

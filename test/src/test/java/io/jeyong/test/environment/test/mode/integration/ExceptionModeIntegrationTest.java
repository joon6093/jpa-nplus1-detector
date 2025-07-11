package io.jeyong.test.environment.test.mode.integration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.jeyong.detector.annotation.NPlusOneTest;
import io.jeyong.test.case2.service.ProductService;
import io.jeyong.test.case4.service.AddressService;
import io.jeyong.test.case4.service.PersonService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@NPlusOneTest(mode = NPlusOneTest.Mode.EXCEPTION)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ExceptionModeIntegrationTest {

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

    @Disabled("AfterEachCallback을 통해 발생하는 예외는 테스트할 수 없다.")
    @Test
    @DisplayName("@SpringBootTest를 이용한 API를 호출하는 상황에서 EXCEPTION 모드가 동작한다.")
    void testExceptionModeInApiCall() {
        String url = "http://localhost:" + port + "/api/products";
        ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Disabled("AfterEachCallback을 통해 발생하는 예외는 테스트할 수 없다.")
    @Test
    @DisplayName("@SpringBootTest를 이용한 Business Logic을 호출에서 상황에서 EXCEPTION 모드가 동작한다.")
    void testExceptionModeInBusinessLogicCall() {
        productService.findAllProducts();
    }

    @Disabled("AfterEachCallback을 통해 발생하는 예외는 테스트할 수 없다.")
    @Test
    @DisplayName("@SpringBootTest를 이용한 여러번의 예외 발생 상황에서 EXCEPTION 모드가 동작한다.")
    void testExceptionModeInMultipleExceptions() {
        productService.findAllProducts();
        addressService.findAllAddresses();
        personService.findAllPersons();
    }
}

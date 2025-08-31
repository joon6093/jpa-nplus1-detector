package tests.environment.test.integration

import io.jeyong.nplus1detector.test.annotation.NPlusOneTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.ResponseEntity
import tests.service.ProductService

@NPlusOneTest(mode = NPlusOneTest.Mode.EXCEPTION)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExceptionModeIntegrationTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var productService: ProductService

    @Disabled("TestExecutionListener를 통해 발생하는 예외는 테스트할 수 없다.")
    @Test
    @DisplayName("@SpringBootTest를 이용한 API를 호출하는 상황에서 EXCEPTION 모드가 동작한다.")
    fun testExceptionModeInApiCall() {
        val url = "http://localhost:$port/api/products"
        val response: ResponseEntity<Void> = restTemplate.getForEntity(url, Void::class.java)

        assertThat(response.statusCode.is2xxSuccessful).isTrue()
    }

    @Disabled("TestExecutionListener를 통해 발생하는 예외는 테스트할 수 없다.")
    @Test
    @DisplayName("@SpringBootTest를 이용한 Business Logic을 호출에서 상황에서 EXCEPTION 모드가 동작한다.")
    fun testExceptionModeInBusinessLogicCall() {
        productService.findAllProducts()
    }
}

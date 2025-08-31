package tests.environment.test.integration

import io.jeyong.nplus1detector.test.annotation.NPlusOneTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.ResponseEntity
import tests.service.ProductService

@NPlusOneTest(mode = NPlusOneTest.Mode.LOGGING)
@ExtendWith(OutputCaptureExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoggingModeIntegrationTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var productService: ProductService

    @Test
    @DisplayName("@SpringBootTest를 이용한 API를 호출하는 상황에서 LOGGING 모드가 동작한다.")
    fun testLoggingModeInApiCall(output: CapturedOutput) {
        val url = "http://localhost:$port/api/products"
        val response: ResponseEntity<Void> = restTemplate.getForEntity(url, Void::class.java)

        assertThat(response.statusCode.is2xxSuccessful).isTrue()
        assertThat(output).contains("N+1 query detected")
    }

    @Test
    @DisplayName("@SpringBootTest를 이용한 Business Logic을 호출에서 상황에서 LOGGING 모드가 동작한다.")
    fun testLoggingModeInBusinessLogicCall(output: CapturedOutput) {
        productService.findAllProducts()
        assertThat(output).contains("N+1 query detected")
    }
}

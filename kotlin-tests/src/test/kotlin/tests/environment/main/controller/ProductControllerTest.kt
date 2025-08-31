package tests.environment.main.controller

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

@ExtendWith(OutputCaptureExtension::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "spring.jpa.properties.hibernate.detector.enabled=true",
        "spring.jpa.properties.hibernate.detector.threshold=2",
        "spring.jpa.properties.hibernate.detector.exclude=",
        "spring.jpa.properties.hibernate.detector.level=warn"
    ]
)
class ProductControllerTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    @DisplayName("N:1 관계에서 다(N)를 조회하는 상황에서 감지한다.")
    fun testGetProducts(output: CapturedOutput) {
        val url = "http://localhost:$port/api/products"
        val response: ResponseEntity<Void> = restTemplate.getForEntity(url, Void::class.java)

        assertThat(response.statusCode.is2xxSuccessful).isTrue()
        assertThat(output).contains("N+1 query detected")
    }
}

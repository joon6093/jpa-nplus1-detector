package io.jeyong.test.environment.main.service

import io.jeyong.test.service.ProductService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension

@ExtendWith(OutputCaptureExtension::class)
@SpringBootTest(
    properties = [
        "spring.jpa.properties.hibernate.detector.enabled=true",
        "spring.jpa.properties.hibernate.detector.threshold=2",
        "spring.jpa.properties.hibernate.detector.exclude=",
        "spring.jpa.properties.hibernate.detector.level=warn"
    ]
)
class ProductServiceTest {

    @Autowired
    private lateinit var productService: ProductService

    @Test
    @DisplayName("다(N)를 조회하는 상황에서 감지한다.")
    fun testFindAllProducts(output: CapturedOutput) {
        productService.findAllProducts()

        assertThat(output).contains("N+1 query detected")
    }
}

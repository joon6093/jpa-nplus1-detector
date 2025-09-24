package tests.environment.main.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import tests.service.ProductService

@ExtendWith(OutputCaptureExtension::class)
@SpringBootTest(
    properties = [
        "nplus1detector.enabled=true",
        "nplus1detector.threshold=2",
        "nplus1detector.exclude=",
        "nplus1detector.level=warn"
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

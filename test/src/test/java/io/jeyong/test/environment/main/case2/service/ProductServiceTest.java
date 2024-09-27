package io.jeyong.test.environment.main.case2.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import io.jeyong.test.case2.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("다(N)를 조회하는 상황에서 감지한다.")
    void testFindAllProducts(CapturedOutput output) {
        productService.findAllProducts();

        assertThat(output).contains("N+1 query detected");
    }
}

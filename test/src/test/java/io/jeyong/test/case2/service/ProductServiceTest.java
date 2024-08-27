package io.jeyong.test.case2.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

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

    /**
     * @formatter:off
     * Product and Order have a many-to-one (N:1) relationship.
     * This test verifies if an N+1 issue occurs when fetching all Products,
     * which then trigger separate queries to fetch the associated Order for each Product.
     * @formatter:on
     */
    @Test
    void testNPlusOneDetection(CapturedOutput output) {
        productService.findAllProducts();

        assertThat(output).contains("N+1 issue detected");
    }
}

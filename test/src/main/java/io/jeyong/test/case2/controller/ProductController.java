package io.jeyong.test.case2.controller;

import io.jeyong.test.case2.entity.Product;
import io.jeyong.test.case2.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * @formatter:off
     * Product와 Order는 다대일(N:1) 관계이며,
     * 모든 Product를 조회한 후 각 Product에 대해 별도의 쿼리로 Order를 조회
     * @formatter:on
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }
}

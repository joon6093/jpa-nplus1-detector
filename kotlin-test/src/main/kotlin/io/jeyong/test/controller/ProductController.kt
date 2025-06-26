package io.jeyong.test.controller

import io.jeyong.test.entity.Product
import io.jeyong.test.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService
) {

    /**
     * Product와 Order는 다대일(N:1) 관계이며,
     * 모든 Product를 조회한 후 각 Product에 대해 별도의 쿼리로 Order를 조회
     * N:1 관계에서 다(N)를 조회하는 상황에서 감지하는 것을 검증
     */
    @GetMapping
    fun getAllProducts(): List<Product> {
        return productService.findAllProducts()
    }
}

package io.jeyong.test.service

import io.jeyong.test.entity.Product
import io.jeyong.test.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    
    @Transactional
    fun findAllProducts(): List<Product> {
        val products = productRepository.findAll()
        products.forEach { it.order?.orderNumber }  // N+1 문제 발생
        return products
    }
}

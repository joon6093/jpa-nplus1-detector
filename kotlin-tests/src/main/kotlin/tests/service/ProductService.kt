package tests.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tests.entity.Product
import tests.repository.ProductRepository

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

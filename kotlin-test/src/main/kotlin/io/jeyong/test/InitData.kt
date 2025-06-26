package io.jeyong.test

import io.jeyong.test.entity.Order
import io.jeyong.test.entity.Product
import io.jeyong.test.repository.OrderRepository
import io.jeyong.test.repository.ProductRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class InitData(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
) {

    @EventListener(ApplicationReadyEvent::class)
    @Transactional
    fun initData() {
        val order1 = Order().apply { orderNumber = "Order 1" }
        val order2 = Order().apply { orderNumber = "Order 2" }
        val order3 = Order().apply { orderNumber = "Order 3" }

        orderRepository.saveAll(listOf(order1, order2, order3))

        val product1 = Product().apply {
            name = "Product 1"
            price = 100.0
            order = order1
        }

        val product2 = Product().apply {
            name = "Product 2"
            price = 200.0
            order = order1
        }

        val product3 = Product().apply {
            name = "Product 3"
            price = 300.0
            order = order2
        }

        val product4 = Product().apply {
            name = "Product 4"
            price = 150.0
            order = order3
        }

        productRepository.saveAll(listOf(product1, product2, product3, product4))
    }
}

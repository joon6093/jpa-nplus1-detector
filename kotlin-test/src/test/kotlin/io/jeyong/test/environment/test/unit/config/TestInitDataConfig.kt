package io.jeyong.test.environment.test.unit.config

import io.jeyong.test.InitData
import io.jeyong.test.repository.OrderRepository
import io.jeyong.test.repository.ProductRepository
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestInitDataConfig {

    @Bean
    fun initData(
        orderRepository: OrderRepository,
        productRepository: ProductRepository,
    ): InitData {
        return InitData(orderRepository, productRepository)
    }
}

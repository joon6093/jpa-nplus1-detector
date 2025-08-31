package tests.environment.test.unit.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import tests.InitData
import tests.repository.OrderRepository
import tests.repository.ProductRepository

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

package io.jeyong.test.exception;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.jeyong.detector.template.NPlusOneQueryExceptionThrower;
import io.jeyong.detector.template.NPlusOneQueryTemplate;
import io.jeyong.detector.test.NPlusOneQueryException;
import io.jeyong.detector.test.NPlusOneTestConfig;
import io.jeyong.test.case2.entity.Order;
import io.jeyong.test.case2.entity.Product;
import io.jeyong.test.case2.repository.OrderRepository;
import io.jeyong.test.case2.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(NPlusOneTestConfig.class)
@DataJpaTest
class ExceptionThrowerTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager em;

    private final NPlusOneQueryTemplate nPlusOneQueryTemplate = new NPlusOneQueryExceptionThrower(2);

    @Test
    @DisplayName("테스트 코드에서는 예외가 발생한다.")
    void testExceptionThrown() {
        // given
        setupTestData();
        clearPersistenceContext();
        // when
        List<Product> products = productRepository.findAll();
        products.forEach(product -> product.getOrder().getOrderNumber());
        // then
        assertThatThrownBy(nPlusOneQueryTemplate::handleNPlusOneIssues)
                .isInstanceOf(NPlusOneQueryException.class)
                .hasMessageContaining("N+1 issue detected");
    }

    private void clearPersistenceContext() {
        em.flush();
        em.clear();
    }

    private void setupTestData() {
        Order order1 = new Order();
        order1.setOrderNumber("Order 1");

        Order order2 = new Order();
        order2.setOrderNumber("Order 2");

        Order order3 = new Order();
        order3.setOrderNumber("Order 3");

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(100.0);
        product1.setOrder(order1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(200.0);
        product2.setOrder(order1);

        Product product3 = new Product();
        product3.setName("Product 3");
        product3.setPrice(300.0);
        product3.setOrder(order2);

        Product product4 = new Product();
        product4.setName("Product 4");
        product4.setPrice(150.0);
        product4.setOrder(order3);

        productRepository.saveAll(List.of(product1, product2, product3, product4));
    }
}

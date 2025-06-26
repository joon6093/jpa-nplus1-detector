package io.jeyong.test.case2.repository;

import io.jeyong.test.case2.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

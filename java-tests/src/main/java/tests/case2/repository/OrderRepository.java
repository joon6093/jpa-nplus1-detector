package tests.case2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tests.case2.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

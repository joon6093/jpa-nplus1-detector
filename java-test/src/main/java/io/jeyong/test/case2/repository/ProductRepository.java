package io.jeyong.test.case2.repository;

import io.jeyong.test.case2.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

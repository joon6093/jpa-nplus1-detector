package tests.case2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tests.case2.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}

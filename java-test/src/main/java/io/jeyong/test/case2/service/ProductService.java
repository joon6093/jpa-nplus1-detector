package io.jeyong.test.case2.service;

import io.jeyong.test.case2.entity.Product;
import io.jeyong.test.case2.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        products.forEach(product -> product.getOrder().getOrderNumber());  // N+1 문제 발생
        return products;
    }
}

package io.jeyong.test.repository

import io.jeyong.test.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>

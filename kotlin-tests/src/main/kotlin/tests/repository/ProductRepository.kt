package tests.repository

import org.springframework.data.jpa.repository.JpaRepository
import tests.entity.Product

interface ProductRepository : JpaRepository<Product, Long>

package tests.repository

import org.springframework.data.jpa.repository.JpaRepository
import tests.entity.Order

interface OrderRepository : JpaRepository<Order, Long>

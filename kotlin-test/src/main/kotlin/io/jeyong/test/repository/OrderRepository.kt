package io.jeyong.test.repository

import io.jeyong.test.entity.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long>

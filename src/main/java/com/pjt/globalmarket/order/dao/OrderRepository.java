package com.pjt.globalmarket.order.dao;

import com.pjt.globalmarket.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

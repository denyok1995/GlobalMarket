package com.pjt.globalmarket.order.dao;

import com.pjt.globalmarket.order.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}

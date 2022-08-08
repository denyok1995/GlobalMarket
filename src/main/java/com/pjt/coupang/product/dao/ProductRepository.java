package com.pjt.coupang.product.dao;

import com.pjt.coupang.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllBy(Pageable pageable);

    Page<Product> findAllByName(String name, Pageable pageable);
}

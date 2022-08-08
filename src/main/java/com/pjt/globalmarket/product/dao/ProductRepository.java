package com.pjt.globalmarket.product.dao;

import com.pjt.globalmarket.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllBy(Pageable pageable);

    Page<Product> findAllByName(String name, Pageable pageable);
}

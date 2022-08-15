package com.pjt.globalmarket.product.dao;

import com.pjt.globalmarket.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllBy(Pageable pageable);

    Page<Product> findAllByName(String name, Pageable pageable);

    List<Product> findAllByIdIn(List<Long> id);
}

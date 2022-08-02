package com.pjt.coupang.product.service;

import com.pjt.coupang.product.dao.ProductRepository;
import com.pjt.coupang.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findProducts(pageable);
    };
}

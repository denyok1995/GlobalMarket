package com.pjt.coupang.product.service;

import com.pjt.coupang.product.dao.ProductRepository;
import com.pjt.coupang.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAllBy(pageable);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}

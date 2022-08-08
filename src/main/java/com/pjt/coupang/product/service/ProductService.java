package com.pjt.coupang.product.service;

import com.pjt.coupang.product.dao.CategoryRepository;
import com.pjt.coupang.product.dao.ProductRepository;
import com.pjt.coupang.product.domain.Category;
import com.pjt.coupang.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAllBy(pageable);
    }

    @Transactional
    public void saveProduct(Product product, Set<String> categories) {
        categories.forEach(name -> {
            Optional<Category> category = categoryRepository.findCategoryByName(name);
            if (category.isPresent()) {
                product.addCategory(category.get());
            } else {
                Category newCategory = Category.builder()
                        .name(name)
                        .build();
                categoryRepository.save(newCategory);
                product.addCategory(newCategory);
            }
        });
        productRepository.save(product);
    }
}

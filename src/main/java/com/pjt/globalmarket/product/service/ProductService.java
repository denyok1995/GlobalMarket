package com.pjt.globalmarket.product.service;

import com.pjt.globalmarket.product.dao.CategoryRepository;
import com.pjt.globalmarket.product.dao.ProductRepository;
import com.pjt.globalmarket.product.domain.Category;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.user.domain.UserGrade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private static final String IS_NOT_USER = "default";
    Map<String, Double> discount = new HashMap<>();

    @PostConstruct
    public void init (){
        this.discount.put(IS_NOT_USER, 1.0); //아직 로그인 하지 않은 회원
        this.discount.put(UserGrade.BRONZE.getGrade(), 0.99 ); //1% 할인율
        this.discount.put(UserGrade.SILVER.getGrade(), 0.97 ); //3% 할인율
        this.discount.put(UserGrade.GOLD.getGrade(), 0.95 ); //5% 할인율
        this.discount.put(UserGrade.DIAMOND.getGrade(), 0.9 ); //10% 할인율
    }

    public Double getDiscountedPriceByUserGrade(UserGrade userGrade, Double price) {
        return price * discount.get((userGrade == null) ? IS_NOT_USER : userGrade.getGrade());
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAllBy(pageable);
    }

    @Transactional
    public Product saveProduct(Product product, Set<String> categories) {
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
        return product;
    }

    public Page<Product> searchProductsByContent(String content, Pageable pageable) {
        return productRepository.findAllByName(content, pageable);
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> findProductsByIds(List<Long> productIds) {
        return productRepository.findAllByIdIn(productIds);
    }
}

package com.pjt.globalmarket.review.dao;

import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findReviewsByProduct(Product product);
}

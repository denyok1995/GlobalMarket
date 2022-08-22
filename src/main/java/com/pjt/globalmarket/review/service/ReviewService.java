package com.pjt.globalmarket.review.service;

import ch.qos.logback.classic.spi.IThrowableProxy;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.review.dao.ReviewRepository;
import com.pjt.globalmarket.review.domain.Review;
import com.pjt.globalmarket.review.dto.WriteReviewInfo;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserService userService;
    private final ProductService productService;
    private final ReviewRepository reviewRepository;

    public void saveReview(String email, String provider, WriteReviewInfo reviewInfo) {
        User user = userService.getActiveUserByEmailAndProvider(email, provider).orElseThrow();
        Product product = productService.getProductById(reviewInfo.getProductId()).orElseThrow();

        Review review = Review.builder().user(user)
                .product(product)
                .score(reviewInfo.getScore())
                .content(reviewInfo.getContent())
                .build();

        reviewRepository.save(review);
    }
}

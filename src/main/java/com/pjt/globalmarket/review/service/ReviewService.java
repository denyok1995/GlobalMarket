package com.pjt.globalmarket.review.service;

import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.review.dao.EvaluationReviewRepository;
import com.pjt.globalmarket.review.dao.ReviewRepository;
import com.pjt.globalmarket.review.domain.EvaluationReview;
import com.pjt.globalmarket.review.domain.Review;
import com.pjt.globalmarket.review.dto.EvaluateReviewInfo;
import com.pjt.globalmarket.review.dto.WriteReviewInfo;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserService userService;
    private final ProductService productService;
    private final ReviewRepository reviewRepository;
    private final EvaluationReviewRepository evaluationReviewRepository;

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

    public List<Review> getReviews(Long productId) {
        Product product = productService.getProductById(productId).orElseThrow();
        return reviewRepository.findReviewsByProduct(product);
    }

    @Transactional
    public void saveEvaluationReview(String email, String provider, EvaluateReviewInfo evaluateReviewInfo) {
        User user = userService.getActiveUserByEmailAndProvider(email, provider).orElseThrow();
        Review review = reviewRepository.findById(evaluateReviewInfo.getReviewId()).orElseThrow();

        EvaluationReview evaluationReview = EvaluationReview.builder().user(user)
                .review(review)
                .isHelp(evaluateReviewInfo.getIsHelp())
                .build();

        evaluationReviewRepository.save(evaluationReview);

        if(evaluateReviewInfo.getIsHelp()) {
            review.setHelpNum(review.getHelpNum() + 1);
        } else {
            review.setNoHelpNum(review.getNoHelpNum() + 1);
        }
    }
}

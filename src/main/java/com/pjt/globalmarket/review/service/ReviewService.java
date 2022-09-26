package com.pjt.globalmarket.review.service;

import com.pjt.globalmarket.product.dao.ProductRepository;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.review.dao.EvaluationReviewRepository;
import com.pjt.globalmarket.review.dao.ReviewRepository;
import com.pjt.globalmarket.review.domain.EvaluationReview;
import com.pjt.globalmarket.review.domain.Review;
import com.pjt.globalmarket.review.dto.EvaluateReviewInfo;
import com.pjt.globalmarket.review.dto.WriteReviewInfo;
import com.pjt.globalmarket.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final EvaluationReviewRepository evaluationReviewRepository;
    private final ProductRepository productRepository;

    public Review saveReview(User user, Product product, WriteReviewInfo reviewInfo) {
        Review review = Review.builder().user(user)
                .product(product)
                .score(reviewInfo.getScore())
                .content(reviewInfo.getContent())
                .build();

        List<Review> reviews = getReviews(product);

        double score = reviewInfo.getScore();
        for(Review savedReview : reviews) {
            score += savedReview.getScore();
        }
        product.setScore(score/(reviews.size()+1));
        productRepository.save(product);

        reviewRepository.save(review);
        return review;
    }

    public List<Review> getReviews(Product product) {
        return reviewRepository.findReviewsByProduct(product);
    }

    @Transactional
    public EvaluationReview saveEvaluationReview(User user, EvaluateReviewInfo evaluateReviewInfo) {
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
        return evaluationReview;
    }
}

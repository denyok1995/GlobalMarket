package com.pjt.globalmarket.review.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.review.dto.EvaluateReviewInfo;
import com.pjt.globalmarket.review.dto.ReviewInfo;
import com.pjt.globalmarket.review.dto.WriteReviewInfo;
import com.pjt.globalmarket.review.service.ReviewService;
import com.pjt.globalmarket.user.domain.NeedLogin;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ProductService productService;

    @NeedLogin
    @PostMapping()
    public void writeReview(@AuthenticationPrincipal UserAuthDetails loginUser,
                            @RequestBody WriteReviewInfo review) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        Product product = productService.getProductById(review.getProductId()).orElseThrow();
        reviewService.saveReview(user, product, review);
    }

    @GetMapping
    public List<ReviewInfo> getReviews(@RequestParam Long productId) {
        Product product = productService.getProductById(productId).orElseThrow();
        return reviewService.getReviews(product).stream().map(review -> {
            return ReviewInfo.builder().email(review.getUser().getEmail())
                    .score(review.getScore())
                    .content(review.getContent())
                    .helpNum(review.getHelpNum())
                    .noHelpNum(review.getNoHelpNum())
                    .createdAt(review.getCreatedAt())
                    .build();
        }).collect(Collectors.toList());
    }

    @NeedLogin
    @PostMapping(path = "/evaluation")
    public void evaluateReview(@AuthenticationPrincipal UserAuthDetails loginUser,
                               @RequestBody EvaluateReviewInfo evaluateReviewInfo) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        reviewService.saveEvaluationReview(user, evaluateReviewInfo);
    }
}

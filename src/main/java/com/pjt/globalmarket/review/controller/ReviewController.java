package com.pjt.globalmarket.review.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.review.dto.EvaluateReviewInfo;
import com.pjt.globalmarket.review.dto.ReviewInfo;
import com.pjt.globalmarket.review.dto.WriteReviewInfo;
import com.pjt.globalmarket.review.service.ReviewService;
import com.pjt.globalmarket.user.domain.NeedLogin;
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

    @NeedLogin
    @PostMapping()
    public void writeReview(@AuthenticationPrincipal UserAuthDetails loginUser,
                            @RequestBody WriteReviewInfo review) {
        reviewService.saveReview(loginUser.getUsername(), loginUser.getProvider(), review);
    }

    @GetMapping
    public List<ReviewInfo> getReviews(@RequestParam Long productId) {
        return reviewService.getReivews(productId).stream().map(review -> {
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
    @PostMapping(path = "/evaluate")
    public void evaluateReview(@AuthenticationPrincipal UserAuthDetails loginUser,
                               @RequestBody EvaluateReviewInfo evaluateReviewInfo) {
        reviewService.saveEvaluationReview(loginUser.getUsername(), loginUser.getProvider(), evaluateReviewInfo);
    }
}

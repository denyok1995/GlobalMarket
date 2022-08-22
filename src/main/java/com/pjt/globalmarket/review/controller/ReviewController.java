package com.pjt.globalmarket.review.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.review.dto.WriteReviewInfo;
import com.pjt.globalmarket.review.service.ReviewService;
import com.pjt.globalmarket.user.domain.NeedLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

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
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "리뷰 작성", notes = "상품 리뷰 정보를 저장한다.")
    public void writeReview(@AuthenticationPrincipal UserAuthDetails loginUser,
                            @RequestBody WriteReviewInfo review) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        Product product = productService.getProductById(review.getProductId()).orElseThrow();
        reviewService.saveReview(user, product, review);
    }

    @GetMapping
    @ApiOperation(value = "리뷰 조회", notes = "해당 상품에 작성된 리뷰를 조회한다.")
    public List<ReviewInfo> getReviews(@RequestParam Long productId) {
        // NOTE: 아래와 같은 문장이 반복되는데, 이걸 단순화 시킬 수 있을까요? @AuthenticationPrincipal와 같은 방법을 도입하면 되지 않을까요?
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
    @ApiOperation(value = "리뷰 평가", notes = "작성된 리뷰를 평가한다.")
    public void evaluateReview(@AuthenticationPrincipal UserAuthDetails loginUser,
                               @RequestBody EvaluateReviewInfo evaluateReviewInfo) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        reviewService.saveEvaluationReview(user, evaluateReviewInfo);
    }
}

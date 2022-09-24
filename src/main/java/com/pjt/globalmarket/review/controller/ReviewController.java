package com.pjt.globalmarket.review.controller;

import com.nimbusds.oauth2.sdk.ErrorResponse;
import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.review.dto.*;
import com.pjt.globalmarket.review.service.ReviewService;
import com.pjt.globalmarket.common.annotation.NeedLogin;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ProductService productService;

    @NeedLogin
    @PostMapping(path = "/review")
    @ApiOperation(value = "리뷰 작성", notes = "상품 리뷰 정보를 저장한다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "리뷰 저장 완료", response = ReviewInfo.class),
            @ApiResponse(code = 403, message = "로그인 하지 않은 요청", response = ErrorResponse.class)
    })
    public ReviewCreateInfo writeReview(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                                        @RequestBody WriteReviewInfo review) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        Product product = productService.getProductById(review.getProductId()).orElseThrow();
        return ReviewCreateInfo.toDto(reviewService.saveReview(user, product, review));
    }

    @GetMapping(path = "/reviews")
    @ApiOperation(value = "리뷰 조회", notes = "해당 상품에 작성된 리뷰를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "리뷰 조회 성공", response = ReviewInfo.class)
    })
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
    @PostMapping(path = "/review/evaluation")
    @ApiOperation(value = "리뷰 평가", notes = "작성된 리뷰를 평가한다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "리뷰 평가 성공", response = EvaluateReviewCreateInfo.class),
            @ApiResponse(code = 403, message = "로그인 하지 않은 요청", response = ErrorResponse.class)
    })
    public EvaluateReviewCreateInfo evaluateReview(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                                                   @RequestBody EvaluateReviewInfo evaluateReviewInfo) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        return EvaluateReviewCreateInfo.toDto(reviewService.saveEvaluationReview(user, evaluateReviewInfo));
    }
}

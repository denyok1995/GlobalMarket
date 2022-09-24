package com.pjt.globalmarket.coupon.controller;

import com.nimbusds.oauth2.sdk.ErrorResponse;
import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.coupon.domain.Coupon;
import com.pjt.globalmarket.coupon.dto.CouponDto;
import com.pjt.globalmarket.coupon.dto.UserCouponInfo;
import com.pjt.globalmarket.coupon.service.CouponService;
import com.pjt.globalmarket.common.annotation.NeedLogin;
import com.pjt.globalmarket.common.annotation.OnlyManager;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;
    private final UserService userService;


    @OnlyManager
    @GetMapping(path = "/coupons/manager/available")
    @ApiOperation(value = "전체 쿠폰 조회", notes = "사용자에게 발급 가능한 모든 쿠폰을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = CouponDto.class),
            @ApiResponse(code = 403, message = "로그인 하지 않은 요청", response = ErrorResponse.class)
    })
    public List<CouponDto> getAvailableCoupons(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser) {
        return couponService.getAllAvailableCoupons().stream().map(coupon -> {
            return CouponDto.builder().id(coupon.getId())
                    .name(coupon.getName())
                    .minPrice(coupon.getMinPrice())
                    .discountPrice(coupon.getDiscountPrice())
                    //.productId(coupon.getProductId())
                    .maxCouponCount(coupon.getMaxCouponCount())
                    .expirationTime(coupon.getExpirationTime())
                    .build();
        }).collect(Collectors.toList());
    }

    @NeedLogin
    @GetMapping(path = "/coupon")
    @ApiOperation(value = "가지고 있는 전체 쿠폰 조회", notes = "사용자가 가지고 있는 모든 쿠폰을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공", response = UserCouponInfo.class),
            @ApiResponse(code = 403, message = "로그인 하지 않은 요청", response = ErrorResponse.class)

    })
    public List<UserCouponInfo> getUserCoupons(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        return couponService.getUserCoupon(user).stream().map(userCoupon -> {
            return UserCouponInfo.builder().id(userCoupon.getId())
                    .name(userCoupon.getCoupon().getName())
                    .discountPrice(userCoupon.getCoupon().getDiscountPrice())
                    .minPrice(userCoupon.getCoupon().getMinPrice())
                    .count(userCoupon.getIssuedCount() - userCoupon.getUseCount())
                    .build();
        }).collect(Collectors.toList());
    }

    //쿠폰 발급
    @NeedLogin
    @PostMapping(path = "/coupon/issue")
    @ApiOperation(value = "쿠폰 발급", notes = "사용자에게 쿠폰을 발급한다.")
    public void issueCoupon(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                           @RequestBody Long couponId) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        Coupon coupon = couponService.getCouponById(couponId).orElseThrow();
        couponService.issueCoupon(user, coupon);
    }

    //쿠폰 생성
    @OnlyManager
    @PostMapping(path = "/coupon/manager")
    @ApiOperation(value = "쿠폰 생성", notes = "쿠폰을 저장한다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "쿠폰 저장 성공", response = CouponDto.class),
            @ApiResponse(code = 403, message = "로그인 하지 않은 요청", response = ErrorResponse.class)
    })
    public void saveCoupon(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                           @RequestBody CouponDto dto) {
        couponService.saveCoupon(dto);
    }
}

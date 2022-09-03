package com.pjt.globalmarket.coupon.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.coupon.domain.Coupon;
import com.pjt.globalmarket.coupon.dto.CouponDto;
import com.pjt.globalmarket.coupon.dto.UserCouponInfo;
import com.pjt.globalmarket.coupon.service.CouponService;
import com.pjt.globalmarket.user.domain.NeedLogin;
import com.pjt.globalmarket.user.domain.OnlyManager;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/coupon")
public class CouponController {

    private final CouponService couponService;
    private final UserService userService;


    @GetMapping(path = "/available")
    public List<CouponDto> getAvailableCoupons() {
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
    @GetMapping
    public List<UserCouponInfo> getUserCoupons(@AuthenticationPrincipal UserAuthDetails loginUser) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        return couponService.getUserCoupon(user).stream().map(userCoupon -> {
            return UserCouponInfo.builder().id(userCoupon.getId())
                    .name(userCoupon.getCoupon().getName())
                    .discountPrice(userCoupon.getCoupon().getDiscountPrice())
                    .minPrice(userCoupon.getCoupon().getMinPrice())
                    .build();
        }).collect(Collectors.toList());
    }

    //쿠폰 발급
    @NeedLogin
    @PostMapping(path = "/issue")
    public void issueCoupon(@AuthenticationPrincipal UserAuthDetails loginUser,
                           @RequestBody Long couponId) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        Coupon coupon = couponService.getCouponById(couponId).orElseThrow();
        couponService.issueCoupon(user, coupon);
    }

    //쿠폰 생성
    @OnlyManager
    @PostMapping(path = "/manager")
    public void saveCoupon(@AuthenticationPrincipal UserAuthDetails loginUser,
                           @RequestBody CouponDto dto) {
        couponService.saveCoupon(dto);
    }
}

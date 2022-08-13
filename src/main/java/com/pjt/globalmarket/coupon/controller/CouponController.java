package com.pjt.globalmarket.coupon.controller;

import com.pjt.globalmarket.coupon.dao.CouponRepository;
import com.pjt.globalmarket.coupon.domain.Coupon;
import com.pjt.globalmarket.coupon.dto.CouponDto;
import com.pjt.globalmarket.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/coupon")
public class CouponController {

    private final CouponService couponService;

    @GetMapping(path = "/auth/available")
    public List<CouponDto> getAvailableCoupons() {
        return couponService.getAllAvailableCoupons().stream().map(coupon -> {
            return CouponDto.builder().id(coupon.getId())
                    .name(coupon.getName())
                    .minPrice(coupon.getMinPrice())
                    .discountPrice(coupon.getDiscountPrice())
                    .productId(coupon.getProductId())
                    .maxCouponCount(coupon.getMaxCouponCount())
                    .expirationTime(coupon.getExpirationTime())
                    .build();
        }).collect(Collectors.toList());
    }

}
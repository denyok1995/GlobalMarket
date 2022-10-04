package com.pjt.globalmarket.marketing.dto;

import com.pjt.globalmarket.coupon.domain.CouponType;
import com.pjt.globalmarket.marketing.domain.WelcomeCoupon;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WelcomeCouponInfo {

    private long id;

    private CouponType couponType;

    private long couponId;

    private ZonedDateTime startDate;

    private ZonedDateTime expiredDate;

    public static WelcomeCouponInfo toDto(WelcomeCoupon welcomeCoupon) {
        return WelcomeCouponInfo.builder()
                .id(welcomeCoupon.getId())
                .couponType(welcomeCoupon.getCouponType())
                .couponId(welcomeCoupon.getCouponId())
                .startDate(welcomeCoupon.getStartDate())
                .expiredDate(welcomeCoupon.getExpiredDate())
                .build();
    }
}

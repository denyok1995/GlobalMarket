package com.pjt.globalmarket.marketing.dto;

import com.pjt.globalmarket.coupon.domain.CouponType;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class WelcomeCouponSetInfo {

    private CouponType couponType;

    private long couponId;

    private ZonedDateTime startDate;

    private ZonedDateTime expiredDate;
}

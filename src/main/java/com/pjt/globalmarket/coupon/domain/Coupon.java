package com.pjt.globalmarket.coupon.domain;

import com.pjt.globalmarket.coupon.dto.CreateCouponRequestInfo;
import com.pjt.globalmarket.coupon.dto.IntegratedCoupon;

public interface Coupon<T> {

    double getDiscountPrice(double price);
}

package com.pjt.globalmarket.coupon.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PercentCouponCommand implements CouponCommand {

    private PercentCoupon percentCoupon;


    @Override
    public double getDiscountPrice(double price) {
        return Math.min(percentCoupon.getMaxDiscountPrice(), calculateDiscountPrice(price));
    }

    private double calculateDiscountPrice(double price) {
        return price * (100 - percentCoupon.getDiscountPercent()) / 100;
    }
}

package com.pjt.globalmarket.coupon.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCouponCommand implements CouponCommand {

    private ProductCoupon productCoupon;

    @Override
    public double getDiscountPrice(double price) {
        return Math.min(productCoupon.getMaxDiscountPrice(), calculateDiscountPrice(price));
    }

    private double calculateDiscountPrice(double price) {
        return price * (100 - productCoupon.getDiscountPercent()) / 100;
    }
}

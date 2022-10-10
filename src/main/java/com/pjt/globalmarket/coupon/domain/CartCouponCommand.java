package com.pjt.globalmarket.coupon.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartCouponCommand implements CouponCommand {

    private CartCoupon cartCoupon;

    @Override
    public double getDiscountPrice(double price) {
        if(price < cartCoupon.getMinPrice()) {
            return 0;
        }
        return Math.min(cartCoupon.getMaxDiscountPrice(), calculateDiscountPrice(price));
    }

    private double calculateDiscountPrice(double price) {
        return price * (100 - cartCoupon.getDiscountPercent()) / 100;
    }
}

package com.pjt.globalmarket.coupon.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceCouponCommand implements CouponCommand {

    private PriceCoupon priceCoupon;

    @Override
    public double getDiscountPrice(double price) {
        if(price < priceCoupon.getMinPrice()) {
            return 0;
        }
        return Math.min(priceCoupon.getDiscountPrice(), price);
    }
}

package com.pjt.globalmarket.coupon.domain;

import lombok.Setter;

@Setter
public class Coupon {

    private CouponCommand couponCommand;

    // 할인이 되는 금액을 리턴한다. (할인된 금액 x)
    public double calculate(double price) {
        return couponCommand.getDiscountPrice(price);
    }
}

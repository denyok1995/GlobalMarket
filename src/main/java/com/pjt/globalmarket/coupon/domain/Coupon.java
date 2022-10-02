package com.pjt.globalmarket.coupon.domain;

public interface Coupon<T> {

    double getDiscountPrice(double price);
}

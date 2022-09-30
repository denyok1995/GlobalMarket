package com.pjt.globalmarket.coupon.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pjt.globalmarket.coupon.dto.CreateCouponRequestInfo;
import com.pjt.globalmarket.coupon.dto.IntegratedCoupon;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CartCoupon implements Coupon<CartCoupon> {

    @Id
    @GeneratedValue
    private long id;

    private String couponId;

    private String name;

    private double discountPercent;

    private double minPrice;

    private double maxDiscountPrice;

    private boolean overlap;

    private long maxCouponCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime expiredDate;

    @Override
    public double getDiscountPrice(double price) {
        return Math.min(maxDiscountPrice, calculateDiscountPrice(price));
    }

    private double calculateDiscountPrice(double price) {
        return price * (100 - discountPercent) / 100;
    }

    public static CartCoupon toEntity(IntegratedCoupon dto) {
        return CartCoupon.builder()
                .id(dto.getId())
                .couponId(dto.getCouponId())
                .name(dto.getName())
                .discountPercent(dto.getDiscountPercent())
                .minPrice(dto.getMinPrice())
                .maxDiscountPrice(dto.getMaxDiscountPrice())
                .overlap(dto.isOverlap())
                .maxCouponCount(dto.getMaxCouponCount())
                .expiredDate(dto.getExpiredDate())
                .build();
    }

    public static CartCoupon toEntity(CreateCouponRequestInfo dto) {
        return CartCoupon.builder()
                .couponId(dto.getCouponId())
                .name(dto.getName())
                .discountPercent(dto.getDiscountPercent())
                .minPrice(dto.getMinPrice())
                .maxDiscountPrice(dto.getMaxDiscountPrice())
                .overlap(dto.isOverlap())
                .maxCouponCount(dto.getMaxCouponCount())
                .expiredDate(dto.getExpiredDate())
                .build();
    }
}

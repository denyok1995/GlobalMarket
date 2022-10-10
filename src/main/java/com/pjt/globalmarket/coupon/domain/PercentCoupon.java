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
@Builder
public class PercentCoupon {

    @Id
    @GeneratedValue
    private long id;

    private String couponId;

    private String name;

    private double discountPercent;

    private double maxDiscountPrice;

    private long maxCouponCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime expiredDate;


    public double getDiscountPrice(double price) {
        return Math.min(maxDiscountPrice, calculateDiscountPrice(price));
    }

    private double calculateDiscountPrice(double price) {
        return price * (100 - discountPercent) / 100;
    }

    public static PercentCoupon toEntity(IntegratedCoupon dto) {
        return PercentCoupon.builder()
                .id(dto.getId())
                .couponId(dto.getCouponId())
                .name(dto.getName())
                .discountPercent(dto.getDiscountPercent())
                .maxDiscountPrice(dto.getMaxDiscountPrice())
                .maxCouponCount(dto.getMaxCouponCount())
                .expiredDate(dto.getExpiredDate())
                .build();
    }

    public static PercentCoupon toEntity(CreateCouponRequestInfo dto) {
        return PercentCoupon.builder()
                .couponId(dto.getCouponId())
                .name(dto.getName())
                .discountPercent(dto.getDiscountPercent())
                .maxDiscountPrice(dto.getMaxDiscountPrice())
                .maxCouponCount(dto.getMaxCouponCount())
                .expiredDate(dto.getExpiredDate())
                .build();
    }
}

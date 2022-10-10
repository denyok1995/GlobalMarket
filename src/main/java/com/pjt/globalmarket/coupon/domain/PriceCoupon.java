package com.pjt.globalmarket.coupon.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pjt.globalmarket.coupon.dto.CreateCouponRequestInfo;
import com.pjt.globalmarket.coupon.dto.IntegratedCoupon;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;

//전체적인 쿠폰에 대한 개요 (발급 가능한 쿠폰 즉, 운영자가 복제하기 위해 만들어둔 쿠폰)
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PriceCoupon {

    @Id
    @GeneratedValue
    private long id;

    private String couponId;

    private String name;

    private double minPrice;

    private double discountPrice;

    private long maxCouponCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime expiredDate;


    public double getDiscountPrice(double price) {
        if(price < minPrice) {
            return 0;
        }
        return Math.min(discountPrice, price);
    }

    public static PriceCoupon toEntity(IntegratedCoupon dto) {
        return PriceCoupon.builder()
                .id(dto.getId())
                .couponId(dto.getCouponId())
                .name(dto.getName())
                .minPrice(dto.getMinPrice())
                .discountPrice(dto.getDiscountPrice())
                .maxCouponCount(dto.getMaxCouponCount())
                .expiredDate(dto.getExpiredDate())
                .build();
    }

    public static PriceCoupon toEntity(CreateCouponRequestInfo dto) {
        return PriceCoupon.builder()
                .couponId(dto.getCouponId())
                .name(dto.getName())
                .minPrice(dto.getMinPrice())
                .discountPrice(dto.getDiscountPrice())
                .maxCouponCount(dto.getMaxCouponCount())
                .expiredDate(dto.getExpiredDate())
                .build();
    }
}

package com.pjt.globalmarket.coupon.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pjt.globalmarket.coupon.dto.CreateCouponRequestInfo;
import com.pjt.globalmarket.coupon.dto.IntegratedCoupon;
import com.pjt.globalmarket.product.domain.Product;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ProductCoupon {

    @Id
    @GeneratedValue
    private long id;

    private String couponId;

    private String name;

    private double discountPercent;

    private double maxDiscountPrice;

    private long minProductCount;

    @ManyToMany
    private List<Product> products;

    private long maxCouponCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime expiredDate;


    public double getDiscountPrice(double price) {
        return Math.min(maxDiscountPrice, calculateDiscountPrice(price));
    }

    private double calculateDiscountPrice(double price) {
        return price * (100 - discountPercent) / 100;
    }

    public static ProductCoupon toEntity(IntegratedCoupon dto) {
        return ProductCoupon.builder()
                .id(dto.getId())
                .couponId(dto.getCouponId())
                .name(dto.getName())
                .discountPercent(dto.getDiscountPercent())
                .maxDiscountPrice(dto.getMaxDiscountPrice())
                .minProductCount(dto.getMinProductCount())
                .products(dto.getProducts())
                .maxCouponCount(dto.getMaxCouponCount())
                .expiredDate(dto.getExpiredDate())
                .build();
    }


    public static ProductCoupon toEntity(CreateCouponRequestInfo dto, List<Product> products) {
        return ProductCoupon.builder()
                .couponId(dto.getCouponId())
                .name(dto.getName())
                .discountPercent(dto.getDiscountPercent())
                .maxDiscountPrice(dto.getMaxDiscountPrice())
                .minProductCount(dto.getMinProductCount())
                .products(products)
                .maxCouponCount(dto.getMaxCouponCount())
                .expiredDate(dto.getExpiredDate())
                .build();
    }
}

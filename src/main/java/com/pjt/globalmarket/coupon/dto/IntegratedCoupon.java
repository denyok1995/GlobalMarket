package com.pjt.globalmarket.coupon.dto;

import com.pjt.globalmarket.coupon.domain.*;
import com.pjt.globalmarket.product.domain.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class IntegratedCoupon {

    private long id;

    private CouponType couponType;

    private String name;

    private String couponId;

    private double minPrice;

    private double discountPrice;

    private double discountPercent;

    private double maxDiscountPrice;

    private long minProductCount;

    @Builder.Default
    private List<Product> products = new ArrayList<>();

    private long maxCouponCount;

    private boolean overlap;

    private ZonedDateTime expiredDate;


    public static IntegratedCoupon toDto(PriceCoupon priceCoupon) {
        return IntegratedCoupon.builder()
                .id(priceCoupon.getId())
                .couponType(CouponType.PRICE)
                .couponId(priceCoupon.getCouponId())
                .name(priceCoupon.getName())
                .minPrice(priceCoupon.getMinPrice())
                .discountPrice(priceCoupon.getDiscountPrice())
                .maxCouponCount(priceCoupon.getMaxCouponCount())
                .expiredDate(priceCoupon.getExpiredDate())
                .build();
    }

    public static IntegratedCoupon toDto(PercentCoupon percentCoupon) {
        return IntegratedCoupon.builder()
                .id(percentCoupon.getId())
                .couponType(CouponType.PERCENT)
                .couponId(percentCoupon.getCouponId())
                .name(percentCoupon.getName())
                .discountPercent(percentCoupon.getDiscountPercent())
                .maxDiscountPrice(percentCoupon.getMaxDiscountPrice())
                .maxCouponCount(percentCoupon.getMaxCouponCount())
                .expiredDate(percentCoupon.getExpiredDate())
                .build();
    }

    public static IntegratedCoupon toDto(ProductCoupon productCoupon) {
        return IntegratedCoupon.builder()
                .id(productCoupon.getId())
                .couponType(CouponType.PRODUCT)
                .couponId(productCoupon.getCouponId())
                .name(productCoupon.getName())
                .discountPercent(productCoupon.getDiscountPercent())
                .minProductCount(productCoupon.getMinProductCount())
                .products(productCoupon.getProducts())
                .maxCouponCount(productCoupon.getMaxCouponCount())
                .expiredDate(productCoupon.getExpiredDate())
                .build();
    }

    public static IntegratedCoupon toDto(CartCoupon cartCoupon) {
        return IntegratedCoupon.builder()
                .id(cartCoupon.getId())
                .couponType(CouponType.CART)
                .couponId(cartCoupon.getCouponId())
                .name(cartCoupon.getName())
                .discountPercent(cartCoupon.getDiscountPercent())
                .minPrice(cartCoupon.getMinPrice())
                .maxDiscountPrice(cartCoupon.getMaxDiscountPrice())
                .overlap(cartCoupon.isOverlap())
                .maxCouponCount(cartCoupon.getMaxCouponCount())
                .expiredDate(cartCoupon.getExpiredDate())
                .build();
    }
}

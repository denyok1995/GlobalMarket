package com.pjt.globalmarket.coupon.dto;

import lombok.*;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CouponDto {

    private Long id;

    private String name;

    private Long minPrice;

    private Long discountPrice;

    //private Long productId;

    private Long maxCouponCount;

    private ZonedDateTime expirationTime;
}

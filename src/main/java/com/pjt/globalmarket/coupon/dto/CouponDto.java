package com.pjt.globalmarket.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CouponDto {

    private Long id;

    private String name;

    private Long minPrice;

    private Long discountPrice;

    private Long productId;

    private Long maxCouponCount;

    private ZonedDateTime expirationTime;
}

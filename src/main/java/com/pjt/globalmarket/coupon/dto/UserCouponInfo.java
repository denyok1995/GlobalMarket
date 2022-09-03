package com.pjt.globalmarket.coupon.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCouponInfo {

    private Long id;

    private String name;

    // 쿠폰 사용 최소 금액
    private Long minPrice;

    private Long discountPrice;
}

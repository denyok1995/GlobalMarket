package com.pjt.globalmarket.coupon.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ActivateCouponInfo {

    @ApiModelProperty(name = "발급된 쿠폰 고유 번호", example = "3", required = true)
    private long id;

    @ApiModelProperty(name = "쿠폰 이름", example = "3월 봄맞이 할인 쿠폰")
    private String name;

    @ApiModelProperty(name = "할인 금액", example = "1000.0")
    private double discountPrice;
}

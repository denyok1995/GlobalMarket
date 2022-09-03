package com.pjt.globalmarket.coupon.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CouponDto {

    @ApiModelProperty(name = "쿠폰 고유 아이디", example = "2")
    private Long id;

    @ApiModelProperty(name = "쿠폰 이름", example = "3월 봄맞이 할인 쿠폰")
    private String name;

    // 쿠폰 사용 최소 금액
    @ApiModelProperty(name = "쿠폰 사용을 위한 최소 금액", example = "20000")
    private Long minPrice;

    @ApiModelProperty(name = "할인 금액", example = "1000")
    private Long discountPrice;

    //private Long productId;

    @ApiModelProperty(name = "최대 발급 개수", example = "300000")
    private Long maxCouponCount;

    @ApiModelProperty(name = "쿠폰 만료 기간", example = "2022-09-03T10:06:53.778Z")
    private ZonedDateTime expirationTime;
}

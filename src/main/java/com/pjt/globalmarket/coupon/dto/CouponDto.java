package com.pjt.globalmarket.coupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ApiModel(description = "쿠폰 정보")
public class CouponDto {

    @ApiModelProperty(notes = "쿠폰 고유 아이디", example = "2")
    private Long id;

    @ApiModelProperty(notes = "쿠폰 이름", example = "3월 봄맞이 할인 쿠폰", required = true)
    private String name;

    // 쿠폰 사용 최소 금액
    @ApiModelProperty(notes = "쿠폰 사용을 위한 최소 금액", example = "20000", required = true)
    private Long minPrice;

    @ApiModelProperty(notes = "할인 금액", example = "1000", required = true)
    private Long discountPrice;

    //private Long productId;

    @ApiModelProperty(notes = "최대 발급 개수", example = "300000", required = true)
    private Long maxCouponCount;

    @ApiModelProperty(notes = "쿠폰 만료 기간", example = "2023-09-10T10:06:53.778Z", required = true)
    private ZonedDateTime expirationTime;
}

package com.pjt.globalmarket.coupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ApiModel(description = "유저 쿠폰 정보")
public class UserCouponInfo {

    @ApiModelProperty(name = "발급된 쿠폰 고유 번호", example = "3", required = true)
    private Long id;

    // NOTE : coupon id로 전부 해결하는거 생각 - 할인 금액까지
    @ApiModelProperty(name = "쿠폰 이름", example = "3월 봄맞이 할인 쿠폰")
    private String name;

    // 쿠폰 사용 최소 금액
    @ApiModelProperty(name = "쿠폰 사용을 위한 최소 금액", example = "20000")
    private Long minPrice;

    @ApiModelProperty(name = "할인 금액", example = "1000")
    private Long discountPrice;

    @ApiModelProperty(name = "쿠폰 개수", example = "2")
    private Long count;
}

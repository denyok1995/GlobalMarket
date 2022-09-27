package com.pjt.globalmarket.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pjt.globalmarket.coupon.domain.UserCoupon;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ApiModel(description = "사용자 쿠폰 정보")
public class UserCouponInfo {

    @ApiModelProperty(name = "발급된 쿠폰 고유 번호", example = "3", required = true)
    private long id;

    // NOTE : coupon id로 전부 해결하는거 생각 - 할인 금액까지
    @ApiModelProperty(name = "쿠폰 이름", example = "3월 봄맞이 할인 쿠폰")
    private String name;

    // 쿠폰 사용 최소 금액
    @ApiModelProperty(name = "쿠폰 사용을 위한 최소 금액", example = "20000.0")
    private double minPrice;

    @ApiModelProperty(name = "할인 금액", example = "1000.0")
    private double discountPrice;

    @ApiModelProperty(name = "쿠폰 개수", example = "2")
    private long count;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime expirationTime;

    public static UserCouponInfo toDto(UserCoupon userCoupon) {
        return UserCouponInfo.builder()
                .id(userCoupon.getId())
                .name(userCoupon.getCoupon().getName())
                .minPrice(userCoupon.getCoupon().getMinPrice())
                .discountPrice(userCoupon.getCoupon().getDiscountPrice())
                .count(userCoupon.getIssuedCount() - userCoupon.getUseCount())
                .expirationTime(userCoupon.getExpirationTime())
                .build();
    }
}

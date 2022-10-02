package com.pjt.globalmarket.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pjt.globalmarket.coupon.domain.CouponType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCouponRequestInfo {

    @ApiModelProperty(notes = "쿠폰 종류", example = "PRICE", required = true)
    private CouponType couponType;

    @ApiModelProperty(notes = "보여줄 쿠폰 이름", example = "3월 봄맞이 할인 쿠폰", required = true)
    private String name;

    @ApiModelProperty(notes = "쿠폰 식별 이름", example = "2022년 1월 신규 회원 쿠폰", required = true)
    private String couponId;

    @ApiModelProperty(notes = "쿠폰 사용을 위한 최소 금액", example = "20000.0", required = true)
    private double minPrice;

    @ApiModelProperty(notes = "할인 금액", example = "1000.0", required = false)
    private double discountPrice;

    @ApiModelProperty(notes = "할인 율", example = "10", required = false)
    private double discountPercent;

    @ApiModelProperty(notes = "최대 할인 금액", example = "2000.0", required = false)
    private double maxDiscountPrice;

    @ApiModelProperty(notes = "최소 상품 개수", example = "3", required = false)
    private long minProductCount;

    @ApiModelProperty(notes = "할인이 적용되는 상품들", example = "[1,2,4]", required = false)
    private List<Long> productIds;

    @ApiModelProperty(notes = "최대 발급 개수", example = "300000", required = true)
    private long maxCouponCount;

    @ApiModelProperty(notes = "쿠폰 중복 적용 가능 여부", example = "true", required = false)
    private boolean overlap;

    @ApiModelProperty(notes = "쿠폰 만료 기간", example = "2023-10-01T05:20:43.292Z", required = true)
    private ZonedDateTime expiredDate;
}

package com.pjt.globalmarket.coupon.dto;

import com.pjt.globalmarket.coupon.domain.CouponType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class IssueCouponInfo {

    @ApiModelProperty(notes = "발급하려는 쿠폰 아이디", example = "8", required = true)
    private long id;

    @ApiModelProperty(notes = "발급하려는 쿠폰 형태", example = "PRICE", required = true)
    private CouponType couponType;

    @ApiModelProperty(notes = "쿠폰 사용 기간", example = "2023-10-01T05:20:43.292Z", required = false)
    private ZonedDateTime expiredDate;
}

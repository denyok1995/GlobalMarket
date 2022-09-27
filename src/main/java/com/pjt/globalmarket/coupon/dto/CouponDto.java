package com.pjt.globalmarket.coupon.dto;

import com.pjt.globalmarket.coupon.domain.Coupon;
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
    private long id;

    @ApiModelProperty(notes = "쿠폰 이름", example = "3월 봄맞이 할인 쿠폰", required = true)
    private String name;

    // 쿠폰 사용 최소 금액
    @ApiModelProperty(notes = "쿠폰 사용을 위한 최소 금액", example = "20000.0", required = true)
    private double minPrice;

    @ApiModelProperty(notes = "할인 금액", example = "1000.0", required = true)
    private double discountPrice;

    //private Long productId;

    @ApiModelProperty(notes = "최대 발급 개수", example = "300000", required = true)
    private long maxCouponCount;


    public static CouponDto toDto(Coupon coupon) {
        return CouponDto.builder()
                .id(coupon.getId())
                .name(coupon.getName())
                .minPrice(coupon.getMinPrice())
                .discountPrice(coupon.getDiscountPrice())
                .maxCouponCount(coupon.getMaxCouponCount())
                .build();
    }
}

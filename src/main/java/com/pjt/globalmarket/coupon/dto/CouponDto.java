package com.pjt.globalmarket.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pjt.globalmarket.coupon.domain.CouponType;
import com.pjt.globalmarket.product.dto.ProductResponseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ApiModel(description = "쿠폰 정보")
public class CouponDto {

    @ApiModelProperty(notes = "쿠폰 고유 아이디", example = "10")
    private long id;

    @ApiModelProperty(notes = "쿠폰 종류", example = "PRICE")
    private CouponType couponType;

    @ApiModelProperty(notes = "보여줄 쿠폰 이름", example = "3월 봄맞이 할인 쿠폰")
    private String name;

    @ApiModelProperty(notes = "쿠폰 식별 이름", example = "2022년 1월 신규 회원 쿠폰")
    private String couponId;

    @ApiModelProperty(notes = "쿠폰 사용을 위한 최소 금액", example = "20000.0")
    private double minPrice;

    @ApiModelProperty(notes = "할인 금액", example = "1000.0")
    private double discountPrice;

    @ApiModelProperty(notes = "할인 율", example = "10")
    private double discountPercent;

    @ApiModelProperty(notes = "최대 할인 금액", example = "2000.0")
    private double maxDiscountPrice;

    @ApiModelProperty(notes = "최소 상품 개수", example = "3")
    private long minProductCount;

    @ApiModelProperty(notes = "할인이 적용되는 상품들", example = "[1,2,4]")
    private List<ProductResponseDto> products;

    @ApiModelProperty(notes = "최대 발급 개수", example = "300000")
    private long maxCouponCount;

    @ApiModelProperty(notes = "중복 사용 가능 여부", example = "true")
    private boolean overlap;

    @ApiModelProperty(notes = "쿠폰 만료 기간", example = "2023-10-01 14:20:43")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime expiredDate;


    public static CouponDto toDto(IntegratedCoupon coupon) {
        return CouponDto.builder()
                .id(coupon.getId())
                .couponType(coupon.getCouponType())
                .name(coupon.getName())
                .couponId(coupon.getCouponId())
                .minPrice(coupon.getMinPrice())
                .discountPrice(coupon.getDiscountPrice())
                .discountPercent(coupon.getDiscountPercent())
                .maxDiscountPrice(coupon.getMaxDiscountPrice())
                .minProductCount(coupon.getMinProductCount())
                .products(coupon.getProducts().stream().map(product -> ProductResponseDto.toDto(product, 1.0)).collect(Collectors.toList()))
                .maxCouponCount(coupon.getMaxCouponCount())
                .overlap(coupon.isOverlap())
                .expiredDate(coupon.getExpiredDate())
                .build();
    }
}

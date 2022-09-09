package com.pjt.globalmarket.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class ProductRequestDto {

    @ApiModelProperty(name = "상품 이름", example = "시계")
    private String name;

    @ApiModelProperty(name = "상품 가격(원)", example = "2000000")
    private Double price;

    @ApiModelProperty(name = "상품 메인 사진")
    private String mainImg;

    @ApiModelProperty(name = "상품 상세 사진")
    private String detailImg;

    @ApiModelProperty(name = "상품 수량(개)", example = "6")
    private Long stock;

    @ApiModelProperty(name = "상품 평점", example = "4.8")
    private Long score;

    @ApiModelProperty(name = "상품 배달 비용", example = "3000")
    private Long deliveryFee;

    @ApiModelProperty(name = "상품 배달 지원 정도", example = "ROCKET_WOW")
    private String rocketDelivery;

    @ApiModelProperty(name = "상품 카테고리", example = "[\"악세서리\"]")
    private Set<String> categories;
}

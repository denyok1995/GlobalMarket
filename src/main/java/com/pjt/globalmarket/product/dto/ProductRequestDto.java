package com.pjt.globalmarket.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@ApiModel(description = "상품 저장 시에 필요한 정보")
public class ProductRequestDto {

    @ApiModelProperty(notes = "상품 이름", example = "시계", required = true)
    private String name;

    @ApiModelProperty(notes = "상품 가격(원)", example = "2000000", required = true)
    private double price;

    @ApiModelProperty(notes = "상품 메인 사진")
    private String mainImg;

    @ApiModelProperty(notes = "상품 상세 사진")
    private String detailImg;

    @ApiModelProperty(notes = "상품 수량(개)", example = "6", required = true)
    private long stock;

    @ApiModelProperty(notes = "상품 평점", example = "4.8")
    private double score;

    @ApiModelProperty(notes = "상품 배달 비용", example = "3000", required = true)
    private long deliveryFee;

    @ApiModelProperty(notes = "상품 배달 지원 정도", example = "ROCKET_WOW", required = true)
    private String rocketDelivery;

    @ApiModelProperty(notes = "상품 카테고리", example = "[\"악세서리\"]", required = true)
    private Set<String> categories;
}

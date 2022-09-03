package com.pjt.globalmarket.cart.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CartDto {

    @ApiModelProperty(name = "장바구니 고유 번호", example = "2")
    private Long id;

    @ApiModelProperty(name = "상품 이름", example = "시계")
    private String productName;

    @ApiModelProperty(name = "상품 개수", example = "1")
    private Long productNum;

    @ApiModelProperty(name = "상품 가격", example = "100000")
    private Double price;

    @ApiModelProperty(name = "상품 배달 금액", example = "20000")
    private Long deliveryFee;

    @ApiModelProperty(name = "상품 배달 지원 정도", example = "ROCKET_WOW")
    private String rocketDelivery;

}

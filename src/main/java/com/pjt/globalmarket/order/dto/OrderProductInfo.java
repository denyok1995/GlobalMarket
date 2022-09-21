package com.pjt.globalmarket.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ApiModel(description = "주문하려는 상품에 대한 정보")
public class OrderProductInfo {

    @ApiModelProperty(notes = "상품 고유 번호", example = "2", required = true)
    private Long id;

    @ApiModelProperty(notes = "상품 이름", example = "2", required = true)
    private String name;

    @ApiModelProperty(notes = "상품 가격", example = "2", required = true)
    private Double price;

    @ApiModelProperty(notes = "주문한 상품 수량", example = "2", required = true)
    private Long count;

    @ApiModelProperty(notes = "배달 금액", example = "2", required = true)
    private Long deliveryFee;

    @ApiModelProperty(notes = "상품 배달 지원 정도", example = "2", required = true)
    private String rocketDelivery;

}

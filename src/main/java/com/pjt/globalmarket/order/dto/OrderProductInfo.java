package com.pjt.globalmarket.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderProductInfo {

    @ApiModelProperty(name = "상품 고유 번호", example = "2")
    private Long id;

    private String name;

    private Double price;

    private Long count;

    private Long deliveryFee;

    private String rocketDelivery;

}

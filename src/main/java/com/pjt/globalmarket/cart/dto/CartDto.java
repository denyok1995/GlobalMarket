package com.pjt.globalmarket.cart.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CartDto {

    private Long id;

    private String productName;

    private Long productNum;

    private Double price;

    private Long deliveryFee;

    private String rocketDelivery;

}

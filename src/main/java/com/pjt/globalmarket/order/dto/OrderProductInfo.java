package com.pjt.globalmarket.order.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderProductInfo {

    private Long id;
    private String name;
    private Double price;
    private Long count;
    private Long deliveryFee;
    private String rocketDelivery;

}

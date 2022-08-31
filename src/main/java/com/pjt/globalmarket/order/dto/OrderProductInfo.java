package com.pjt.globalmarket.order.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderProductInfo {

    private Long id;

    private String name;

    private Double price;

    private Long count;

    private Long deliveryFee;

    private String rocketDelivery;

}

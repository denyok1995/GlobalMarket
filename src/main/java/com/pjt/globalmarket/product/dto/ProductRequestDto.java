package com.pjt.globalmarket.product.dto;

import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class ProductRequestDto {
    private String name;
    private Double price;
    private String mainImg;
    private String detailImg;
    private Long stock;
    private Long score;
    private Long deliveryFee;
    private String rocketDelivery;
    private Set<String> categories;
}

package com.pjt.globalmarket.product.dto;

import com.pjt.globalmarket.product.domain.Category;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class ProductResponseDto {

    private Long id;
    private String name;
    private Double price;
    private Long stock;
    private Long score;
    private Long deliveryFee;
    private String rocketDelivery;
    private Set<Category> categories;
}

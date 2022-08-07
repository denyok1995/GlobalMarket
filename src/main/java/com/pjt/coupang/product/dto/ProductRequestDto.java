package com.pjt.coupang.product.dto;

import com.pjt.coupang.product.domain.Category;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class ProductRequestDto {
    private String name;
    private Long price;
    private String mainImg;
    private String detailImg;
    private Long stock;
    private Long score;
    private Long deliveryFee;
    private String rocketDelivery;
    private Set<Category> categories;
}

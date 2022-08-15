package com.pjt.globalmarket.product.dto;

import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
public class CartProductDto {

    private Long productId;

    private Long productNum;
}

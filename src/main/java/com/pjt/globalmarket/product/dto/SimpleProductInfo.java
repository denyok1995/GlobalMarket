package com.pjt.globalmarket.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
public class SimpleProductInfo {

    @ApiModelProperty(name = "상품 고유 번호(DB)", example = "290")
    private Long productId;

    @ApiModelProperty(name = "상품 개수", example = "2")
    private Long productNum;
}

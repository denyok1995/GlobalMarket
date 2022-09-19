package com.pjt.globalmarket.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@ApiModel(description = "상품의 간단한 아이디 및 수량의 정보")
public class SimpleProductInfo {

    @ApiModelProperty(notes = "상품 고유 번호(DB)", example = "290", required = true)
    private Long productId;

    @ApiModelProperty(notes = "상품 개수", example = "2", required = true)
    private Long productNum;
}

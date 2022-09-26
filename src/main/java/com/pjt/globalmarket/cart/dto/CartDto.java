package com.pjt.globalmarket.cart.dto;

import com.pjt.globalmarket.cart.domain.Cart;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@ApiModel(description = "장바구니 정보")
public class CartDto {

    @ApiModelProperty(notes = "장바구니 고유 번호", example = "2", required = true)
    private long id;

    @ApiModelProperty(notes = "상품 이름", example = "시계")
    private String productName;

    @ApiModelProperty(notes = "상품 개수", example = "1")
    private long productNum;

    @ApiModelProperty(notes = "상품 가격", example = "100000")
    private double price;

    @ApiModelProperty(notes = "상품 배달 금액", example = "20000")
    private long deliveryFee;

    @ApiModelProperty(notes = "상품 배달 지원 정도", example = "ROCKET_WOW")
    private String rocketDelivery;

    public static CartDto toDto(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .productName(cart.getProduct().getName())
                .productNum(cart.getProductNum())
                .price(cart.getProduct().getPrice())
                .deliveryFee(cart.getProduct().getDeliveryFee())
                .rocketDelivery(cart.getProduct().getRocketDelivery())
                .build();
    }

}

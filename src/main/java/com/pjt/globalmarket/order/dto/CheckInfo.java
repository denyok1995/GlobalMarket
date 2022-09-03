package com.pjt.globalmarket.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CheckInfo {

    @ApiModelProperty(name = "구매자 이름", example = "홍길동")
    private String consumerName;

    @ApiModelProperty(name = "구매자 핸드폰 번호", example = "010-1234-5678")
    private String consumerPhone;

    @ApiModelProperty(name = "구매자 주소", example = "서울특별시")
    private String consumerAddress;

    @ApiModelProperty(name = "주문할 상품 정보")
    private List<OrderProductInfo> orderProducts;

    @ApiModelProperty(name = "전체 상품 금액", example = "640000")
    private Double totalPrice;

    @ApiModelProperty(name = "전체 상품 배달 금액", example = "3000")
    private Double totalDeliveryFee;

}

package com.pjt.globalmarket.order.dto;

import com.pjt.globalmarket.order.domain.Order;
import com.pjt.globalmarket.payment.domain.PaymentType;
import com.pjt.globalmarket.payment.dto.PaymentInfo;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@ApiModel(description = "주문 완료 정보")
public class DoOrderInfo {

    @ApiModelProperty(notes = "주문 고유 번호", example = "1")
    private long id;

    @ApiModelProperty(notes = "받는 사람 이름", example = "홍길동")
    private String receiverName;

    @ApiModelProperty(notes = "받는 사람 주소", example = "서울특별시")
    private String receiverAddress;

    @ApiModelProperty(notes = "받는 사람 핸드폰 번호", example = "010-1234-5678")
    private String receiverPhone;

    @ApiModelProperty(notes = "주문 시 요청사항", example = "문 앞에 놔주세요.")
    private String receiverRequest;

    @ApiModelProperty(notes = "주문 상품 정보", example = "[\n{\"productId\": 290,\n\"productNum\": 2\n}\n]")
    private List<SimpleProductInfo> orderProducts;

    @ApiModelProperty(notes = "전체 상품 금액", example = "640000")
    private double totalPrice;

    @ApiModelProperty(notes = "전체 상품 배달 금액", example = "3000")
    private double totalDeliveryFee;


    public static DoOrderInfo toDto(Order order) {
        return DoOrderInfo.builder()
                .id(order.getId())
                .receiverName(order.getReceiverName())
                .receiverAddress(order.getReceiverAddress())
                .receiverPhone(order.getReceiverPhone())
                .receiverRequest(order.getReceiverRequest())
                .orderProducts(order.getOrderProducts().stream().map(orderProduct -> {
                    return SimpleProductInfo.builder()
                            .productId(orderProduct.getId())
                            .productNum(orderProduct.getProductNum())
                            .build();
                }).collect(Collectors.toList()))
                .totalPrice(order.getPayment().getTotalPrice())
                .totalDeliveryFee(order.getPayment().getDeliveryFee())
                .build();
    }
}

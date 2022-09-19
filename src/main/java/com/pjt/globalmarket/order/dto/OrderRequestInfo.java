package com.pjt.globalmarket.order.dto;

import com.pjt.globalmarket.payment.domain.PaymentType;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ApiModel(description = "최종 주문 요청 시에 필요한 정보")
public class OrderRequestInfo {

    @ApiModelProperty(notes = "구매자 이름", example = "홍길동", required = true)
    private String consumerName;

    @ApiModelProperty(notes = "구매자 핸드폰 번호", example = "010-1234-5678", required = true)
    private String consumerPhone;

    @ApiModelProperty(notes = "받는 사람 이름", example = "홍길동", required = true)
    private String receiverName;

    @ApiModelProperty(notes = "받는 사람 주소", example = "서울특별시", required = true)
    private String receiverAddress;

    @ApiModelProperty(notes = "받는 사람 핸드폰 번호", example = "010-1234-5678", required = true)
    private String receiverPhone;

    @ApiModelProperty(notes = "주문 시 요청사항", example = "문 앞에 놔주세요.", required = false)
    private String receiverRequest;

    @ApiModelProperty(notes = "주문 상품 정보", example = "[\n{\"productId\": 290,\n\"productNum\": 2\n}\n]", required = true)
    private List<SimpleProductInfo> orderProducts;

    @ApiModelProperty(notes = "전체 상품 금액", example = "640000", required = true)
    private Double totalPrice;

    @ApiModelProperty(notes = "전체 상품 배달 금액", example = "3000", required = true)
    private Double totalDeliveryFee;

    @ApiModelProperty(notes = "적용 할 쿠폰 고유 번호", example = "5", required = true)
    private Long couponId;

    @ApiModelProperty(notes = "결제 수단", example = "CARD", required = true)
    private PaymentType paymentType;
}

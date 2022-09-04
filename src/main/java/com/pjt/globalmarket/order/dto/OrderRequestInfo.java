package com.pjt.globalmarket.order.dto;

import com.pjt.globalmarket.payment.domain.PaymentType;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRequestInfo {

    @ApiModelProperty(name = "구매자 이름", example = "홍길동")
    private String consumerName;

    @ApiModelProperty(name = "구매자 핸드폰 번호", example = "010-1234-5678")
    private String consumerPhone;

    @ApiModelProperty(name = "받는 사람 이름", example = "홍길동")
    private String receiverName;

    @ApiModelProperty(name = "받는 사람 주소", example = "서울특별시")
    private String receiverAddress;

    @ApiModelProperty(name = "받는 사람 핸드폰 번호", example = "010-1234-5678")
    private String receiverPhone;

    @ApiModelProperty(name = "주문 시 요청사항", example = "문 앞에 놔주세요.")
    private String receiverRequest;

    @ApiModelProperty(name = "주문 상품 정보", example = "[2, 3, 4]")
    private List<SimpleProductInfo> orderProducts;

    @ApiModelProperty(name = "전체 상품 금액", example = "640000")
    private Double totalPrice;

    @ApiModelProperty(name = "전체 상품 배달 금액", example = "3000")
    private Double totalDeliveryFee;

    @ApiModelProperty(name = "적용 할 쿠폰 고유 번호", example = "5")
    private Long couponId;

    @ApiModelProperty(name = "결제 수단", example = "CARD")
    private PaymentType paymentType;
}

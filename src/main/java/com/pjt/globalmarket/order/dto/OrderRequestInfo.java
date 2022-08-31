package com.pjt.globalmarket.order.dto;

import com.pjt.globalmarket.payment.domain.PaymentType;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRequestInfo {

    private String consumerName;

    private String consumerPhone;

    private String receiverName;

    private String receiverAddress;

    private String receiverPhone;

    private String receiverRequest;

    private List<OrderProductInfo> orderProducts;

    private Long totalPrice;

    private Long totalDeliveryFee;

    private Long couponId;

    private PaymentType paymentType;
}

package com.pjt.globalmarket.order.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderRequestInfo {

    private String consumerName;

    private String consumerPhone;

    private String receiverName;

    private String receiverAddress;

    private String receiverPhone;

    private String receiverRequest;

    private List<OrderProductInfo> orderProducts;

    private long totalPrice;

    private long totalDeliveryFee;
}

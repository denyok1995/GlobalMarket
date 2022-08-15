package com.pjt.globalmarket.order.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CheckInfo {

    private String consumerName;

    private String consumerPhone;

    private String consumerAddress;

    private List<OrderProductInfo> orderProducts;

    private Double totalPrice;

    private Double totalDeliveryFee;

}

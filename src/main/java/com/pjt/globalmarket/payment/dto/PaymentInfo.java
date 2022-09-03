package com.pjt.globalmarket.payment.dto;

import com.pjt.globalmarket.payment.domain.PaymentType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentInfo {

    @ApiModelProperty(name = "결제 수단", example = "CARD")
    private PaymentType type;

    @ApiModelProperty(name = "할인 금액", example = "32000")
    private Double discountPrice;

    @ApiModelProperty(name = "전체 금액", example = "640000")
    private Double totalPrice;

}

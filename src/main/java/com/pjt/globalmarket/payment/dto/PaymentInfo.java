package com.pjt.globalmarket.payment.dto;

import com.pjt.globalmarket.payment.domain.PaymentType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ApiModel(description = "결제 정보")
public class PaymentInfo {

    @ApiModelProperty(notes = "결제 수단", example = "CARD", required = true)
    private PaymentType type;

    @ApiModelProperty(notes = "할인 금액", example = "32000", required = true)
    private Double discountPrice;

    @ApiModelProperty(notes = "전체 금액", example = "640000", required = true)
    private Double totalPrice;

}

package com.pjt.globalmarket.payment.dto;

import com.pjt.globalmarket.payment.domain.PaymentType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentInfo {

    private PaymentType type;

    private Double discountPrice;

    private Double totalPrice;

}

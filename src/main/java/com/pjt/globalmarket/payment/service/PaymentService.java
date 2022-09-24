package com.pjt.globalmarket.payment.service;

import com.pjt.globalmarket.payment.dao.PaymentRepository;
import com.pjt.globalmarket.payment.domain.Payment;
import com.pjt.globalmarket.payment.dto.PaymentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void pay(PaymentInfo paymentInfo) {
        savePayment(paymentInfo);
    }

    public Payment savePayment(PaymentInfo info) {
        Payment payment = Payment.builder()
                .type(info.getType())
                .totalPrice(info.getTotalPrice())
                .discountPrice(info.getDiscountPrice())
                .deliveryFee(info.getDeliveryFee())
                .build();

        paymentRepository.save(payment);
        return payment;
    }
}

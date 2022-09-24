package com.pjt.globalmarket.payment.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    private PaymentType type;

    private Double discountPrice;

    private Double totalPrice;

    private Double deliveryFee;

    //private boolean status;

    @CreationTimestamp
    private ZonedDateTime createdAt;
}

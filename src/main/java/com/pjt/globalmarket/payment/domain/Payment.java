package com.pjt.globalmarket.payment.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
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

    //private boolean status;

    @CreationTimestamp
    private ZonedDateTime createdAt;
}

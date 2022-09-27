package com.pjt.globalmarket.coupon.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;
//전체적인 쿠폰에 대한 개요 (발급 가능한 쿠폰 즉, 운영자가 복제하기 위해 만들어둔 쿠폰)
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Coupon {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String name;

    // 쿠폰 사용 최소 금액
    private double minPrice;

    private double discountPrice;

    // 할인 율 (금액과 동시에 사용 불가능)
    private double discountPercent;

    // 최대 할인 금액
    private double maxDiscountPrice;

    //상품에 따라 사용할 수 있는 쿠폰이 다르다.
    //private Long productId;

    //최대로 발급할 수 있는 쿠폰의 수
    private long maxCouponCount;

}

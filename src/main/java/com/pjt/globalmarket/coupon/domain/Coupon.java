package com.pjt.globalmarket.coupon.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

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
    private Long id;

    private String name;

    // 쿠폰 사용 최소 금액
    private Long minPrice;

    private Long discountPrice;

    //상품에 따라 사용할 수 있는 쿠폰이 다르다.
    //private Long productId;

    //최대로 발급할 수 있는 쿠폰의 수
    private Long maxCouponCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime expirationTime;
}

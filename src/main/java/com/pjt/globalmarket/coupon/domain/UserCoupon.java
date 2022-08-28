package com.pjt.globalmarket.coupon.domain;

import com.pjt.globalmarket.user.domain.User;
import lombok.*;

import javax.persistence.*;


//살제 쿠폰이 발급 되었을 때, 유저랑 매칭되는 테이블
@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserCoupon {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User user;

    @OneToOne
    private Coupon coupon;

    //사용된 쿠폰의 수
    @Setter
    @Builder.Default
    private Long useCount = 0L;

    //발급된 쿠폰의 수
    @Setter
    @Builder.Default
    private Long issuedCount = 1L;
}

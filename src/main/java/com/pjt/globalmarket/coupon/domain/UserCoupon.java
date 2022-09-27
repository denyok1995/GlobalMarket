package com.pjt.globalmarket.coupon.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pjt.globalmarket.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;


//살제 쿠폰이 발급 되었을 때, 유저랑 매칭되는 테이블
@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserCoupon {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private User user;

    @OneToOne
    private Coupon coupon;

    //사용된 쿠폰의 수
    @Setter
    @Builder.Default
    private long useCount = 0L;

    //발급된 쿠폰의 수
    @Setter
    @Builder.Default
    private long issuedCount = 1L;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime expirationTime;

}

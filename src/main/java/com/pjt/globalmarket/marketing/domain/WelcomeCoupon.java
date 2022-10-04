package com.pjt.globalmarket.marketing.domain;

import com.pjt.globalmarket.coupon.domain.CouponType;
import com.pjt.globalmarket.marketing.dto.WelcomeCouponSetInfo;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@ToString
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WelcomeCoupon {

    @Id
    @GeneratedValue
    private long id;

    private long couponId;

    private CouponType couponType;

    @Setter
    @Builder.Default
    private boolean welcome = false;

    private ZonedDateTime startDate;

    private ZonedDateTime expiredDate;

    public static WelcomeCoupon toEntity(WelcomeCouponSetInfo setInfo) {
        return WelcomeCoupon.builder()
                .couponId(setInfo.getCouponId())
                .couponType(setInfo.getCouponType())
                .startDate(setInfo.getStartDate())
                .expiredDate(setInfo.getExpiredDate())
                .build();
    }
}

package com.pjt.globalmarket.marketing.dao;

import com.pjt.globalmarket.marketing.domain.WelcomeCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface WelcomeCouponRepository extends JpaRepository<WelcomeCoupon, Long> {

    Optional<WelcomeCoupon> findWelcomeCouponByWelcome(boolean welcome);

    // 유효기간 지난 쿠폰
    List<WelcomeCoupon> findWelcomeCouponsByExpiredDateAfter(ZonedDateTime startDate);

    Optional<WelcomeCoupon> findWelcomeCouponByExpiredDateBeforeAndWelcome(ZonedDateTime startDate, boolean welcome);

    Optional<WelcomeCoupon> findWelcomeCouponByStartDateBeforeAndExpiredDateAfterAndWelcome(ZonedDateTime startDate, ZonedDateTime expiredDate, boolean welcome);
}

package com.pjt.globalmarket.coupon.dao;

import com.pjt.globalmarket.coupon.domain.PercentCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface PercentCouponRepository extends JpaRepository<PercentCoupon, Long> {

    Optional<PercentCoupon> findCouponByCouponId(String couponId);

    List<PercentCoupon> findAllByExpiredDateAfter(ZonedDateTime now);
}

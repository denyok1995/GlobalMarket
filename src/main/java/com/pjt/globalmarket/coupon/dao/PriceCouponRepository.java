package com.pjt.globalmarket.coupon.dao;

import com.pjt.globalmarket.coupon.domain.PriceCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface PriceCouponRepository extends JpaRepository<PriceCoupon, Long> {

    Optional<PriceCoupon> findCouponByCouponId(String couponId);

    List<PriceCoupon> findAllByExpiredDateBefore(ZonedDateTime now);
}

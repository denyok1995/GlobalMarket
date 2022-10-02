package com.pjt.globalmarket.coupon.dao;

import com.pjt.globalmarket.coupon.domain.CartCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface CartCouponRepository extends JpaRepository<CartCoupon, Long> {

    Optional<CartCoupon> findCouponByCouponId(String couponId);

    List<CartCoupon> findAllByExpiredDateAfter(ZonedDateTime now);
}

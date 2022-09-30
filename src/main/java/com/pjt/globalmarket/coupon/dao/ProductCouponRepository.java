package com.pjt.globalmarket.coupon.dao;

import com.pjt.globalmarket.coupon.domain.ProductCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductCouponRepository extends JpaRepository<ProductCoupon, Long> {

    Optional<ProductCoupon> findCouponByCouponId(String couponId);

    List<ProductCoupon> findAllByExpiredDateBefore(ZonedDateTime now);
}

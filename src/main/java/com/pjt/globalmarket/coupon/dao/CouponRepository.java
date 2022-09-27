package com.pjt.globalmarket.coupon.dao;

import com.pjt.globalmarket.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findCouponByName(String name);
}

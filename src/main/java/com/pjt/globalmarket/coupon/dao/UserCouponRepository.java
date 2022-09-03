package com.pjt.globalmarket.coupon.dao;

import com.pjt.globalmarket.coupon.domain.Coupon;
import com.pjt.globalmarket.coupon.domain.UserCoupon;
import com.pjt.globalmarket.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    Optional<UserCoupon> findUserCouponByUserAndCoupon(User user, Coupon coupon);

    List<UserCoupon> findUserCouponsByUser(User user);
}

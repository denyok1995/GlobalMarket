package com.pjt.globalmarket.coupon.service;

import com.pjt.globalmarket.coupon.dao.CouponRepository;
import com.pjt.globalmarket.coupon.dao.UserCouponRepository;
import com.pjt.globalmarket.coupon.domain.Coupon;
import com.pjt.globalmarket.coupon.domain.UserCoupon;
import com.pjt.globalmarket.coupon.dto.CouponDto;
import com.pjt.globalmarket.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    public Optional<Coupon> getCouponById(Long couponId) {
        return couponRepository.findById(couponId);
    }

    public Optional<UserCoupon> getUserCouponById(Long userCouponId) {
        return userCouponRepository.findById(userCouponId);
    }

    public List<Coupon> getAllAvailableCoupons() {
        return couponRepository.findCouponsByExpirationTimeIsGreaterThan(ZonedDateTime.now());
    }

    @Transactional
    public void issueCoupon(User user, Coupon coupon) {
        Optional<UserCoupon> savedUserCoupon = userCouponRepository.findUserCouponByUserAndCoupon(user, coupon);
        if(savedUserCoupon.isPresent() && !isOverIssue(savedUserCoupon.get(), coupon)) {
            savedUserCoupon.get().setIssuedCount(savedUserCoupon.get().getIssuedCount() + 1);
        } else {
            UserCoupon userCoupon = UserCoupon.builder()
                    .user(user)
                    .coupon(coupon)
                    .build();
            userCouponRepository.save(userCoupon);
        }
    }

    public Boolean isOverIssue(UserCoupon userCoupon, Coupon coupon) {
        if(userCoupon.getIssuedCount() >= coupon.getMaxCouponCount()) {
            throw new IllegalArgumentException("최대 발급 횟수를 초과하였습니다.");
        } else {
            return false;
        }
    }

    public void saveCoupon(CouponDto dto) {
        Coupon coupon = Coupon.builder()
                .name(dto.getName())
                .minPrice(dto.getMinPrice())
                .discountPrice(dto.getDiscountPrice())
                .maxCouponCount(dto.getMaxCouponCount())
                .expirationTime(dto.getExpirationTime())
                .build();

        couponRepository.save(coupon);
    }

    public List<UserCoupon> getUserCoupon(User user) {
        return userCouponRepository.findUserCouponsByUser(user).stream().filter(this::isActiveCoupon).collect(Collectors.toList());
    }

    public Boolean isActiveCoupon(UserCoupon userCoupon) {
        if(userCoupon.getIssuedCount() <= userCoupon.getUseCount()) {
            return false;
        }
        if(userCoupon.getCoupon().getExpirationTime().isBefore(ZonedDateTime.now())) {
            return false;
        }
        return true;
    }

    @Transactional
    public void useCoupon(User user, Coupon coupon) {
        Optional<UserCoupon> userCoupon = userCouponRepository.findUserCouponByUserAndCoupon(user, coupon);
        userCoupon.ifPresent(value -> value.setUseCount(value.getUseCount() + 1));
    }
}

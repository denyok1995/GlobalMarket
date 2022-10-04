package com.pjt.globalmarket.marketing.service;

import com.pjt.globalmarket.coupon.dto.UserCouponInfo;
import com.pjt.globalmarket.coupon.service.CouponService;
import com.pjt.globalmarket.marketing.dao.WelcomeCouponRepository;
import com.pjt.globalmarket.marketing.domain.WelcomeCoupon;
import com.pjt.globalmarket.marketing.dto.WelcomeCouponSetInfo;
import com.pjt.globalmarket.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketingService {

    private final CouponService couponService;
    private final WelcomeCouponRepository welcomeCouponRepository;

    public WelcomeCoupon getWelcomeCoupon() {
        return welcomeCouponRepository.findWelcomeCouponByWelcome(true).orElse(new WelcomeCoupon());
    }

    //welcome 쿠폰 지정
    public WelcomeCoupon saveWelcomeCoupon(WelcomeCouponSetInfo setInfo) {
        List<WelcomeCoupon> dateAfter = welcomeCouponRepository.findWelcomeCouponsByExpiredDateAfter(setInfo.getStartDate());
        if(!dateAfter.isEmpty()) {
            throw new IllegalArgumentException("날짜 설정이 잘못 되었습니다.");
        }
        WelcomeCoupon welcomeCoupon = WelcomeCoupon.toEntity(setInfo);
        welcomeCouponRepository.save(welcomeCoupon);
        return welcomeCoupon;
    }

    //welcome 쿠폰 발급
    public UserCouponInfo issueWelcomeCoupon(User user) {
        Optional<WelcomeCoupon> welcomeCoupon = welcomeCouponRepository.findWelcomeCouponByWelcome(true);
        if(welcomeCoupon.isPresent()) {
            return couponService.issueCoupon(user, getWelcomeCoupon().getCouponId()
                    , welcomeCoupon.get().getCouponType(), welcomeCoupon.get().getExpiredDate());
        } else {
            return new UserCouponInfo();
        }
    }

    @Transactional
    @Scheduled(cron = "59 59 23 * * *")
    public void unActivateWelcomeCoupon() {
        Optional<WelcomeCoupon> welcomeCoupon = welcomeCouponRepository.findWelcomeCouponByExpiredDateBeforeAndWelcome(ZonedDateTime.now().plusSeconds(2), true);
        if(welcomeCoupon.isPresent()) {
            log.info("Un Activate Welcome Coupon : {}", welcomeCoupon.get());
            welcomeCoupon.get().setWelcome(false);
        } else {
            log.info("Un Activate not yet");
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void activateWelcomeCoupon() {
        // 활성화 기간은 지났고 만료기간은 지나지 않은 쿠폰 조회
        Optional<WelcomeCoupon> welcomeCoupon = welcomeCouponRepository.findWelcomeCouponByStartDateBeforeAndExpiredDateAfterAndWelcome(ZonedDateTime.now().plusSeconds(1), ZonedDateTime.now(), false);
        if(welcomeCoupon.isPresent()) {
            log.info("Activate Welcome Coupon : {}", welcomeCoupon.get());
            welcomeCoupon.get().setWelcome(true);
        } else {
            log.info("Activate not yet");
        }
    }
}

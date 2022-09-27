package com.pjt.globalmarket.coupon.service;

import com.pjt.globalmarket.coupon.dao.CouponRepository;
import com.pjt.globalmarket.coupon.dao.UserCouponRepository;
import com.pjt.globalmarket.coupon.domain.Coupon;
import com.pjt.globalmarket.coupon.domain.UserCoupon;
import com.pjt.globalmarket.coupon.dto.ActivateCouponInfo;
import com.pjt.globalmarket.coupon.dto.CouponDto;
import com.pjt.globalmarket.product.dao.ProductRepository;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.domain.UserGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pjt.globalmarket.user.domain.UserConstant.NEW_USER_COUPON;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @PostConstruct
    public void createWelcomeCoupon() {
        Optional<Coupon> savedCoupon = couponRepository.findCouponByName(NEW_USER_COUPON);
        if(savedCoupon.isPresent()) {
            return ;
        }
        Coupon coupon = Coupon.builder()
                .name(NEW_USER_COUPON)
                .discountPercent(10)
                .maxDiscountPrice(30000)
                .maxCouponCount(-1)
                .build();
        couponRepository.save(coupon);
    }

    public Optional<Coupon> getCouponById(Long couponId) {
        return couponRepository.findById(couponId);
    }

    public Optional<UserCoupon> getUserCouponById(Long userCouponId) {
        return userCouponRepository.findById(userCouponId);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Transactional
    public UserCoupon issueCoupon(User user, Coupon coupon) {
        Optional<UserCoupon> savedUserCoupon = userCouponRepository.findUserCouponByUserAndCoupon(user, coupon);
        if(savedUserCoupon.isPresent() && !isOverIssue(savedUserCoupon.get(), coupon)) {
            savedUserCoupon.get().setIssuedCount(savedUserCoupon.get().getIssuedCount() + 1);
            return savedUserCoupon.get();
        } else {
            UserCoupon userCoupon = UserCoupon.builder()
                    .user(user)
                    .coupon(coupon)
                    .build();
            userCouponRepository.save(userCoupon);
            return userCoupon;
        }
    }

    public Boolean isOverIssue(UserCoupon userCoupon, Coupon coupon) {
        if(coupon.getMaxCouponCount() == -1) return false;
        if(userCoupon.getIssuedCount() >= coupon.getMaxCouponCount()) {
            throw new IllegalArgumentException("최대 발급 횟수를 초과하였습니다.");
        } else {
            return false;
        }
    }

    public Coupon saveCoupon(CouponDto dto) {
        Coupon coupon = Coupon.builder()
                .name(dto.getName())
                .minPrice(dto.getMinPrice())
                .discountPrice(dto.getDiscountPrice())
                .maxCouponCount(dto.getMaxCouponCount())
                .build();

        Optional<Coupon> savedCoupon = couponRepository.findCouponByName(dto.getName());
        if(savedCoupon.isPresent()) {
            return savedCoupon.get();
        } else {
            couponRepository.save(coupon);
            return coupon;
        }
    }

    public List<UserCoupon> getUserCoupon(User user) {
        return userCouponRepository.findUserCouponsByUser(user).stream().filter(this::isActiveCoupon).collect(Collectors.toList());
    }

    public Boolean isActiveCoupon(UserCoupon userCoupon) {
        if(userCoupon.getIssuedCount() <= userCoupon.getUseCount()) {
            return false;
        }
        if(userCoupon.getExpirationTime().isBefore(ZonedDateTime.now())) {
            return false;
        }
        return true;
    }

    public List<ActivateCouponInfo> getActivateCoupon(User user, List<SimpleProductInfo> simpleProductInfos) {
        List<UserCoupon> userCoupons = getUserCoupon(user);
        if(userCoupons.size() == 0) return new ArrayList<>();

        double totalPrice = getProductsPrice(user.getGrade(), simpleProductInfos);
        for(UserCoupon userCoupon : userCoupons) {

        }
    }

    public double getProductsPrice(UserGrade userGrade, List<SimpleProductInfo> simpleProductInfos) {
        double totalPrice = 0;
        for(SimpleProductInfo simpleProductInfo : simpleProductInfos) {
            Product product = productService.getProductById(simpleProductInfo.getProductId()).get();
            totalPrice += productService.getDiscountedPriceByUserGrade(userGrade, product.getPrice()) * simpleProductInfo.getProductNum();
        }
        return totalPrice;
    }

    public double isOnlyProduct(UserCoupon userCoupon, double price, List<Long> productIds) {

    }

    public boolean isPercentCoupon() {

    }

    public boolean isPriceCoupon() {

    }

    @Transactional
    public void useCoupon(UserCoupon userCoupon) {
        Optional<UserCoupon> issuedCoupon = userCouponRepository.findById(userCoupon.getId());
        issuedCoupon.ifPresent(coupon -> coupon.setUseCount(coupon.getUseCount() + 1));;
    }
}

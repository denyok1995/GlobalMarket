package com.pjt.globalmarket.coupon.service;

import com.pjt.globalmarket.coupon.dao.CouponRepository;
import com.pjt.globalmarket.coupon.dao.UserCouponRepository;
import com.pjt.globalmarket.coupon.domain.Coupon;
import com.pjt.globalmarket.coupon.domain.UserCoupon;
import com.pjt.globalmarket.coupon.dto.ActivateCouponInfo;
import com.pjt.globalmarket.coupon.dto.CouponDto;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.domain.UserGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CouponService {

    private final PriceCouponRepository priceCouponRepository;
    private final PercentCouponRepository percentCouponRepository;
    private final ProductCouponRepository productCouponRepository;
    private final CartCouponRepository cartCouponRepository;
    private final UserCouponRepository userCouponRepository;
    private final ProductService productService;


    public Optional<Coupon> getCouponById(Long couponId) {
        return couponRepository.findById(couponId);
    }

    public Optional<UserCoupon> getUserCouponById(Long userCouponId) {
        return userCouponRepository.findById(userCouponId);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public List<IntegratedCoupon> getAllCoupons() {
        List<IntegratedCoupon> coupons = new ArrayList<>();
        priceCouponRepository.findAllByExpiredDateAfter(ZonedDateTime.now())
                .forEach(priceCoupon -> coupons.add(IntegratedCoupon.toDto(priceCoupon)));
        percentCouponRepository.findAllByExpiredDateAfter(ZonedDateTime.now())
                .forEach(percentCoupon -> coupons.add(IntegratedCoupon.toDto(percentCoupon)));
        productCouponRepository.findAllByExpiredDateAfter(ZonedDateTime.now())
                .forEach(productCoupon -> coupons.add(IntegratedCoupon.toDto(productCoupon)));
        cartCouponRepository.findAllByExpiredDateAfter(ZonedDateTime.now())
                .forEach(cartCoupon -> coupons.add(IntegratedCoupon.toDto(cartCoupon)));
        return coupons;
    }

    @Transactional
    public UserCouponInfo issueCoupon(User user, long id, CouponType couponType, ZonedDateTime expiredDate) {
        Optional<UserCoupon> savedUserCoupon =
                userCouponRepository.findUserCouponByUserAndCouponTypeAndCouponId(user, couponType, id);
        IntegratedCoupon coupon = getCoupon(couponType, id);
        if(savedUserCoupon.isPresent() && !isOverIssue(savedUserCoupon.get(), coupon)) {
            savedUserCoupon.get().setIssuedCount(savedUserCoupon.get().getIssuedCount() + 1);
            return UserCouponInfo.toDto(savedUserCoupon.get(), coupon);
        } else {
            UserCoupon userCoupon = UserCoupon.builder()
                    .user(user)
                    .couponType(couponType)
                    .couponId(id)
                    .expiredDate(setExpiredDate(expiredDate, coupon.getExpiredDate()))
                    .build();
            userCouponRepository.save(userCoupon);
            return UserCouponInfo.toDto(userCoupon, coupon);
        }
    }

    private ZonedDateTime setExpiredDate(ZonedDateTime writeDate, ZonedDateTime savedDate) {
        if(writeDate == null ||writeDate.isAfter(savedDate)) {
            return savedDate;
        } else {
            return writeDate;
        }
    }

    public boolean isOverIssue(UserCoupon userCoupon, IntegratedCoupon coupon) {
        if(coupon.getMaxCouponCount() == -1) return false;
        if(userCoupon.getIssuedCount() >= coupon.getMaxCouponCount()) {
            throw new IllegalArgumentException("최대 발급 횟수를 초과하였습니다.");
        } else {
            return false;
        }
    }

    public IntegratedCoupon saveCoupon(CreateCouponRequestInfo couponRequestInfo) {
        if(couponRequestInfo.getCouponType() == CouponType.PRICE) {
            return savePriceCoupon(couponRequestInfo);
        } else if(couponRequestInfo.getCouponType() == CouponType.PERCENT) {
            return savePercentCoupon(couponRequestInfo);
        } else if(couponRequestInfo.getCouponType() == CouponType.PRODUCT) {
            return saveProductCoupon(couponRequestInfo);
        } else if(couponRequestInfo.getCouponType() == CouponType.CART) {
            return saveCartCoupon(couponRequestInfo);
        }
        return new IntegratedCoupon();
    }

    private IntegratedCoupon savePriceCoupon(CreateCouponRequestInfo couponInfo) {
        PriceCoupon priceCoupon = PriceCoupon.toEntity(couponInfo);
        priceCouponRepository.save(priceCoupon);
        return IntegratedCoupon.toDto(priceCoupon);
    }

    private IntegratedCoupon savePercentCoupon(CreateCouponRequestInfo couponInfo) {
        PercentCoupon percentCoupon = PercentCoupon.toEntity(couponInfo);
        percentCouponRepository.save(percentCoupon);
        return IntegratedCoupon.toDto(percentCoupon);
    }

    private IntegratedCoupon saveProductCoupon(CreateCouponRequestInfo couponInfo) {
        List<Product> products = productService.findProductsByIds(couponInfo.getProductIds());
        ProductCoupon productCoupon = ProductCoupon.toEntity(couponInfo, products);
        productCouponRepository.save(productCoupon);
        return IntegratedCoupon.toDto(productCoupon);
    }

    private IntegratedCoupon saveCartCoupon(CreateCouponRequestInfo couponInfo) {
        CartCoupon cartCoupon = CartCoupon.toEntity(couponInfo);
        cartCouponRepository.save(cartCoupon);
        return IntegratedCoupon.toDto(cartCoupon);
    }

    public List<UserCoupon> getUserCoupon(User user) {
        return userCouponRepository.findUserCouponsByUser(user).stream().filter(this::isActiveCoupon).collect(Collectors.toList());
    }

    public Boolean isActiveCoupon(UserCoupon userCoupon) {
        if(userCoupon.getIssuedCount() <= userCoupon.getUseCount()) {
            return false;
        }
        if(userCoupon.getExpirationTime() == null || userCoupon.getExpirationTime().isBefore(ZonedDateTime.now())) {
            return false;
        }
        return true;
    }

    public List<ActivateCouponInfo> getActivateCoupon(User user, List<SimpleProductInfo> simpleProductInfos) {
        List<UserCoupon> userCoupons = getUserCoupon(user);
        List<ActivateCouponInfo> activateCouponInfos = new ArrayList<>();
        if(userCoupons.isEmpty()) return activateCouponInfos;

        double totalPrice = getProductsPrice(user.getGrade(), simpleProductInfos);
        for(UserCoupon userCoupon : userCoupons) {
            if(!isOverMinPrice(userCoupon.getCoupon(), totalPrice)) continue;

            if(isPercentCoupon(userCoupon)) {
                double discountPrice = totalPrice * (100-userCoupon.getCoupon().getDiscountPercent()) / 100;
                activateCouponInfos.add(getActivateCouponInfo(userCoupon, discountPrice));
            } else if(isPriceCoupon(userCoupon)) {
                activateCouponInfos.add(getActivateCouponInfo(userCoupon, totalPrice - userCoupon.getCoupon().getDiscountPrice()));
            }
        }
        return activateCouponInfos;
    }

    private double getProductsPrice(UserGrade userGrade, List<SimpleProductInfo> simpleProductInfos) {
        double totalPrice = 0;
        for(SimpleProductInfo simpleProductInfo : simpleProductInfos) {
            Product product = productService.getProductById(simpleProductInfo.getProductId()).get();
            totalPrice += productService.getDiscountedPriceByUserGrade(userGrade, product.getPrice()) * simpleProductInfo.getProductNum();
        }
        return totalPrice;
    }

    private boolean isOverMinPrice(Coupon coupon, double price) {
        return price >= coupon.getMinPrice();
    }

    private ActivateCouponInfo getActivateCouponInfo(UserCoupon userCoupon, double price) {
        if(price > userCoupon.getCoupon().getMaxDiscountPrice()) {
            price = userCoupon.getCoupon().getMaxDiscountPrice();
        }
        return ActivateCouponInfo.builder()
                .id(userCoupon.getId())
                .discountPrice(price)
                .name(userCoupon.getCoupon().getName())
                .build();
    }

    private double isOnlyProduct(UserCoupon userCoupon, double price, List<Long> productIds) {
        return 0;
    }

    private boolean isPercentCoupon(UserCoupon userCoupon) {
        return userCoupon.getCoupon().getDiscountPercent() > 0;
    }

    private boolean isPriceCoupon(UserCoupon userCoupon) {
        return userCoupon.getCoupon().getDiscountPrice() > 0;
    }

    @Transactional
    public void useCoupon(UserCoupon userCoupon) {
        Optional<UserCoupon> issuedCoupon = userCouponRepository.findById(userCoupon.getId());
        issuedCoupon.ifPresent(coupon -> coupon.setUseCount(coupon.getUseCount() + 1));;
    }
}

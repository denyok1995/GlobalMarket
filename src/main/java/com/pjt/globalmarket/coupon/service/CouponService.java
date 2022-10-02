package com.pjt.globalmarket.coupon.service;

import com.pjt.globalmarket.cart.dao.CartRepository;
import com.pjt.globalmarket.cart.domain.Cart;
import com.pjt.globalmarket.coupon.dao.*;
import com.pjt.globalmarket.coupon.domain.*;
import com.pjt.globalmarket.coupon.dto.*;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.dto.ProductWithNumInfo;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.domain.UserGrade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final PriceCouponRepository priceCouponRepository;
    private final PercentCouponRepository percentCouponRepository;
    private final ProductCouponRepository productCouponRepository;
    private final CartCouponRepository cartCouponRepository;
    private final UserCouponRepository userCouponRepository;
    private final ProductService productService;
    private final CartRepository cartRepository;

    public Optional<UserCoupon> getUserCouponById(Long userCouponId) {
        return userCouponRepository.findById(userCouponId);
    }

    public IntegratedCoupon getCoupon(CouponType couponType, long id) {
        if(couponType == CouponType.PRICE) {
            return IntegratedCoupon.toDto(priceCouponRepository.findById(id).orElse(new PriceCoupon()));
        } else if(couponType == CouponType.PERCENT) {
            return IntegratedCoupon.toDto(percentCouponRepository.findById(id).orElse(new PercentCoupon()));
        } else if(couponType == CouponType.PRODUCT) {
            return IntegratedCoupon.toDto(productCouponRepository.findById(id).orElse(new ProductCoupon()));
        } else if(couponType == CouponType.CART) {
            return IntegratedCoupon.toDto(cartCouponRepository.findById(id).orElse(new CartCoupon()));
        }
        return new IntegratedCoupon();
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

    public List<UserCouponInfo> getUserCoupon(User user) {
        return userCouponRepository.findUserCouponsByUser(user).stream().filter(this::isActiveCoupon).map(userCoupon -> {
            IntegratedCoupon coupon = getCoupon(userCoupon.getCouponType(), userCoupon.getCouponId());
            return UserCouponInfo.toDto(userCoupon, coupon);
        }).collect(Collectors.toList());
    }

    public Boolean isActiveCoupon(UserCoupon userCoupon) {
        if(userCoupon.getIssuedCount() <= userCoupon.getUseCount()) {
            return false;
        }
        if(userCoupon.getExpiredDate() == null || userCoupon.getExpiredDate().isBefore(ZonedDateTime.now())) {
            return false;
        }
        return true;
    }

    public List<ActivateCouponInfo> getActivateCoupon(User user, List<SimpleProductInfo> simpleProductInfos) {
        List<UserCouponInfo> userCoupons = getUserCoupon(user);
        List<ActivateCouponInfo> activateCouponInfos = new ArrayList<>();
        if(userCoupons.isEmpty()) return activateCouponInfos;

        List<ProductWithNumInfo> products = getProductWithNumBySimpleProduct(user.getGrade(), simpleProductInfos);
        double totalPrice = getProductsTotalPrice(products);
        for(UserCouponInfo userCoupon : userCoupons) {
            ActivateCouponInfo activateCouponInfo = getActivateCouponInfo(user, userCoupon, totalPrice, products);
            if(activateCouponInfo != null) {
                activateCouponInfos.add(activateCouponInfo);
            }
        }
        return activateCouponInfos;
    }

    private List<ProductWithNumInfo> getProductWithNumBySimpleProduct(UserGrade userGrade, List<SimpleProductInfo> simpleProductInfos) {
        return simpleProductInfos.stream().map(simpleProductInfo -> {
            return ProductWithNumInfo.builder()
                    .product(productService.getProductByIdWithUserGrade(userGrade, simpleProductInfo.getProductId()))
                    .num(simpleProductInfo.getProductNum())
                    .build();
        }).collect(Collectors.toList());
    }

    private double getProductsTotalPrice(List<ProductWithNumInfo> productWithNumInfos) {
        double totalPrice = 0;
        for(ProductWithNumInfo productWithNumInfo : productWithNumInfos) {
            totalPrice += productWithNumInfo.getProduct().getPrice() * productWithNumInfo.getNum();
        }
        return totalPrice;
    }

    private ActivateCouponInfo getActivateCouponInfo(User user, UserCouponInfo userCoupon, double price, List<ProductWithNumInfo> products) {
        double discountedPrice = getDiscountedPrice(user, userCoupon, price, products);
        if(discountedPrice > 0) {
            return ActivateCouponInfo.builder()
                    .id(userCoupon.getId())
                    .discountPrice(discountedPrice)
                    .name(userCoupon.getName())
                    .build();
        } else {
            return null;
        }
    }

    private double getDiscountedPrice(User user, UserCouponInfo userCoupon, double price, List<ProductWithNumInfo> products) {
        switch (userCoupon.getCouponType()) {
            case PRICE:
                return getDiscountedPriceWithPriceCoupon(userCoupon, price);
            case PERCENT:
                return getDiscountedPriceWithPercentCoupon(userCoupon, price);
            case PRODUCT:
                return getDiscountedPriceWithProductCoupon(userCoupon, products);
            case CART:
                return getDiscountedPriceWithCartCoupon(user, userCoupon, products);
        }
        return 0;
    }

    private double getDiscountedPriceWithPriceCoupon(UserCouponInfo userCoupon, double price) {
        PriceCoupon coupon = priceCouponRepository.findById(userCoupon.getCouponId()).orElseThrow();
        return coupon.getDiscountPrice(price);
    }

    private double getDiscountedPriceWithPercentCoupon(UserCouponInfo userCoupon, double price) {
        PercentCoupon coupon = percentCouponRepository.findById(userCoupon.getCouponId()).orElseThrow();
        return coupon.getDiscountPrice(price);
    }

    private double getDiscountedPriceWithProductCoupon(UserCouponInfo userCoupon, List<ProductWithNumInfo> products) {
        ProductCoupon coupon = productCouponRepository.findById(userCoupon.getCouponId()).orElseThrow();
        List<ProductWithNumInfo> sameProducts = getSameProductWithNumInfo(products, coupon.getProducts());
        long count = 0;
        for(ProductWithNumInfo info : sameProducts) {
            count += info.getNum();
        }
        if(coupon.getMinProductCount() > count) {
            return 0;
        }
        return coupon.getDiscountPrice(getProductsTotalPrice(sameProducts));
    }

    private double getDiscountedPriceWithCartCoupon(User user, UserCouponInfo userCoupon, List<ProductWithNumInfo> products) {
        CartCoupon coupon = cartCouponRepository.findById(userCoupon.getCouponId()).orElseThrow();
        List<Cart> carts = cartRepository.findCartsByUser(user);
        List<ProductWithNumInfo> sameProducts = getSameProductWithNumInfo(products, carts.stream().map(cart -> {
            return cart.getProduct();
        }).collect(Collectors.toList()));
        return coupon.getDiscountPrice(getProductsTotalPrice(sameProducts));
    }

    private List<ProductWithNumInfo> getSameProductWithNumInfo(List<ProductWithNumInfo> productInfos, List<Product> products) {
        List<ProductWithNumInfo> sameProducts = new ArrayList<>();
        for(ProductWithNumInfo info : productInfos) {
            Optional<Product> first = products.stream().filter(product -> product.getId() == info.getProduct().getId()).findFirst();
            if(first.isPresent()) {
                sameProducts.add(info);
            }
        }
        return sameProducts;
    }

    @Transactional
    public void useCoupon(UserCoupon userCoupon) {
        Optional<UserCoupon> issuedCoupon = userCouponRepository.findById(userCoupon.getId());
        issuedCoupon.ifPresent(coupon -> coupon.setUseCount(coupon.getUseCount() + 1));
    }
}

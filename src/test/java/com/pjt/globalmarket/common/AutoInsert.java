package com.pjt.globalmarket.common;

import com.pjt.globalmarket.coupon.dao.CouponRepository;
import com.pjt.globalmarket.coupon.domain.Coupon;
import com.pjt.globalmarket.product.dao.CategoryRepository;
import com.pjt.globalmarket.product.dao.ProductRepository;
import com.pjt.globalmarket.product.domain.Category;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.user.dao.UserRepository;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.pjt.globalmarket.user.domain.UserConstant.DEFAULT_PROVIDER;

@Component
public class AutoInsert {

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CouponRepository couponRepository;

    public User saveUser() {
        Optional<User> userOptional = userRepository.findUserByEmailAndProviderAndDeletedAt("sa@test.com", DEFAULT_PROVIDER, null);
        if(userOptional.isEmpty()) {
            User user = User.builder("sa@test.com", encoder.encode("password"))
                    .phone("010-1234-5678")
                    .name("테스트 이름")
                    .role(UserRole.ROLE_MANAGER)
                    .build();
            return userRepository.save(user);
        }
        return userOptional.get();
    }

    public long saveProduct() {
        Category category = Category.builder()
                .name("악세서리")
                .build();
        categoryRepository.save(category);
        Product product = Product.builder("시계", 100000.0)
                .stock(50L)
                .score(4L)
                .deliveryFee(2000L)
                .category(new HashSet<>(List.of(category)))
                .rocketDelivery("ROCKET_WOW")
                .build();
        return productRepository.save(product).getId();
    }

    public long saveCoupon() {
        Coupon coupon = Coupon.builder()
                .name("봄맞이 쿠폰")
                .minPrice(10000L)
                .discountPrice(2000L)
                .maxCouponCount(2L)
                .expirationTime(ZonedDateTime.of(2022, 12, 2, 1, 2, 2,2, ZonedDateTime.now().getZone()))
                .build();
        return couponRepository.save(coupon).getId();
    }
}

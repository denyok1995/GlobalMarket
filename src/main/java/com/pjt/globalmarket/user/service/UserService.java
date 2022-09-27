package com.pjt.globalmarket.user.service;

import com.pjt.globalmarket.coupon.dao.CouponRepository;
import com.pjt.globalmarket.coupon.dao.UserCouponRepository;
import com.pjt.globalmarket.coupon.domain.UserCoupon;
import com.pjt.globalmarket.user.dao.UserRepository;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.Optional;

import static com.pjt.globalmarket.user.domain.UserConstant.DEFAULT_PROVIDER;
import static com.pjt.globalmarket.user.domain.UserConstant.NEW_USER_COUPON;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    @PostConstruct
    public void init() {
        if(getActiveUserByEmailAndProvider("manager@coupang.com", DEFAULT_PROVIDER).isPresent()) {
            return ;
        }
        User user = User.builder("manager@coupang.com", encoder.encode("password"))
                .name("manager")
                .role(UserRole.ROLE_MANAGER)
                .phone("010-1234-5678")
                .build();
        userRepository.save(user);
    }

    public User saveUser(String email, String password, String name, String phone) {
        Optional<User> savedUser = getActiveUserByEmailAndProvider(email, DEFAULT_PROVIDER);
        if(savedUser.isPresent()) {
            return savedUser.get();
        }
        User user = User.builder(email, password)
                .name(name)
                .phone(phone)
                .build();
        issueWelcomeCoupon(user);
        userRepository.save(user);
        return user;
    }

    private void issueWelcomeCoupon(User user) {
        couponRepository.findCouponByName(NEW_USER_COUPON).ifPresent(coupon -> {
            UserCoupon userCoupon = UserCoupon.builder()
                    .user(user)
                    .coupon(coupon)
                    .expirationTime(ZonedDateTime.now().plusDays(7))
                    .build();
            userCouponRepository.save(userCoupon);
        });
    }

    public Optional<User> getActiveUserByEmailAndProvider(String email, String provider) {
        return userRepository.findUserByEmailAndProviderAndDeletedAt(email, provider, null);
    }

    public User updateUser(User user, String name, String phone) {
        user.setName(name);
        user.setPhone(phone);
        userRepository.save(user);
        return user;
    }

    public void deleteUser(User user) {
        user.setDeletedAt(ZonedDateTime.now());
        userRepository.save(user);
    }

}

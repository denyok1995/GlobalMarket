package com.pjt.globalmarket.user.service;

import com.pjt.globalmarket.marketing.service.MarketingService;
import com.pjt.globalmarket.user.dao.UserRepository;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.Optional;

import static com.pjt.globalmarket.user.domain.UserConstant.DEFAULT_PROVIDER;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MarketingService marketingService;
    private final BCryptPasswordEncoder encoder;

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

    @Transactional
    public User saveUser(String email, String password, String name, String phone) {
        Optional<User> savedUser = getActiveUserByEmailAndProvider(email, DEFAULT_PROVIDER);
        if(savedUser.isPresent()) {
            return savedUser.get();
        }
        User user = User.builder(email, password)
                .name(name)
                .phone(phone)
                .build();
        userRepository.save(user);
        marketingService.issueWelcomeCoupon(user);
        return user;
    }

    public User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow();
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

package com.pjt.globalmarket.user.service;

import com.pjt.globalmarket.user.dao.UserRepository;
import com.pjt.globalmarket.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @PostConstruct
    public void init() {
        User user = User.builder("manager@coupang.com", encoder.encode("password"))
                .name("manager")
                .role("ROLE_MANAGER")
                .phone("010-1234-5678")
                .build();
        userRepository.save(user);
    }

    public void saveUser(String email, String password, String name, String phone) {
        User user = User.builder(email, password)
                .name(name)
                .phone(phone)
                .build();
        userRepository.save(user);
    }

    public boolean getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @Transactional
    public void updateUser(String email, String password, String name, String phone) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isEmpty()) return;
        if(!encoder.matches(password, user.get().getPassword())) {
            //todo : 예외처리
            return;
        }
        user.get().setName(name);
        user.get().setPhone(phone);
    }

    @Transactional
    public void deleteUser(String email, String password, String provider) {
        Optional<User> user = getActiveUserByEmailAndProvider(email, provider);
        if(user.isEmpty()) {
            // todo : 적당한 에러 리턴
        }
        user.get().setDeletedAt(ZonedDateTime.now());
    }

}

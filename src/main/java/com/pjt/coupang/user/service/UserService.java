package com.pjt.coupang.user.service;

import com.pjt.coupang.user.dao.UserRepository;
import com.pjt.coupang.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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

}

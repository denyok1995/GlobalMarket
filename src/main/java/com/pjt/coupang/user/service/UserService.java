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
    private final SecurityService securityService;

    public void saveUser(String email, String password, String name, String phone) {
        String salt = securityService.generateSalt();
        User user = User.builder(email)
                .password(securityService.hash(password, salt))
                .name(name)
                .phone(phone)
                .build();
        userRepository.save(user);
    }

    public boolean getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    public Optional<User> login(String email, String password) {
        Optional<User> userByEmail = userRepository.findUserByEmail(email);
        if(userByEmail.isPresent()) {
            boolean checkPassword = checkPassword(password, userByEmail);
            if(checkPassword) {
                return userByEmail;
            }
        }
        return Optional.empty();
    }

    private boolean checkPassword(String password, Optional<User> userByEmail) {
        String hash = securityService.hash(password, userByEmail.orElse(new User()).getSalt());
        if(hash.equals(userByEmail.orElse(new User()).getPassword())) {
            return true;
        }
        return false;
    }
}

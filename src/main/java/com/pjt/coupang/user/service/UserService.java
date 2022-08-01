package com.pjt.coupang.user.service;

import com.pjt.coupang.user.dao.UserRepository;
import com.pjt.coupang.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SecurityService securityService;

    public UserService(UserRepository userRepository, SecurityService securityService) {
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    public void saveUser(String email, String password, String name, String phone) {
        String salt = securityService.generateSalt();
        User user = new User(email, securityService.hash(password, salt), name, phone, salt);
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
        String hash = securityService.hash(password, userByEmail.get().getSalt());
        if(hash.equals(userByEmail.get().getPassword())) {
            return true;
        }
        return false;
    }
}

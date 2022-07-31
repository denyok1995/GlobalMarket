package com.pjt.coupang.user.service;

import com.pjt.coupang.user.dao.UserRepository;
import com.pjt.coupang.user.domain.User;
import org.springframework.stereotype.Service;

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


}

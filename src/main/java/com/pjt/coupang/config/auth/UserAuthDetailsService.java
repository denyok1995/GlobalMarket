package com.pjt.coupang.config.auth;

import com.pjt.coupang.user.dao.UserRepository;
import com.pjt.coupang.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    /**
     * @param email the username identifying the user whose data is required.
     *              메서드 명은 username이지만 실제로는 email(사용자 아이디)를 이용하고 있다.
     * @return UserDetails
     * @throws UsernameNotFoundException
     */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("email : "+ email);
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isPresent()) {
            System.out.println("User : "+user.get());
            return new UserAuthDetails(user.get());
        }
        return null;
    }
}

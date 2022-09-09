package com.pjt.globalmarket.config.auth;

import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.pjt.globalmarket.user.domain.UserConstant.DEFAULT_PROVIDER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthDetailsService implements UserDetailsService {

    private final UserService userService;


    /**
     * @param email the username identifying the user whose data is required.
     *              메서드 명은 username이지만 실제로는 email(사용자 아이디)를 이용하고 있다.
     * @return UserDetails
     * @throws UsernameNotFoundException
     */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("로그인 email : {}", email);
        Optional<User> user = userService.getActiveUserByEmailAndProvider(email, DEFAULT_PROVIDER);
        if(user.isPresent()) {
            return new UserAuthDetails(user.get());
        }
        throw new UsernameNotFoundException("Any User Email : " + email);
    }
}

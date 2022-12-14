package com.pjt.globalmarket.config.aop;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoginAspect {

    private UserService userService;

    //@Before(value = "@annotation(com.pjt.globalmarket.common.annotation.NeedLogin)")
    public void checkLogin(JoinPoint joinPoint) {
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        for(Object o : args) {
            if(o instanceof UserAuthDetails) {
                UserAuthDetails user = (UserAuthDetails) o;
                if(userService.getActiveUserByEmailAndProvider(user.getUsername(), user.getProvider()).isEmpty()) {
                    throw new AuthenticationCredentialsNotFoundException("로그인 해야 합니다.");
                } else {
                    log.info("로그인 된 유저입니다.");
                    return;
                }
            }
        }
        throw new AuthenticationCredentialsNotFoundException("로그인 해야 합니다.");
    }
}

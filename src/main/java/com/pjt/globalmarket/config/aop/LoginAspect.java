package com.pjt.globalmarket.config.aop;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
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
public class LoginAspect {

    @Before(value = "@annotation(com.pjt.globalmarket.user.domain.NeedLogin)")
    public void checkLogin(JoinPoint joinPoint) {
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        log.info("Login Check");
        for(Object o : args) {
            if(o instanceof UserAuthDetails) {
                log.info("로그인 된 유저입니다.");
                return;
            }
        }
    }
}

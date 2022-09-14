package com.pjt.globalmarket.config.aop;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.pjt.globalmarket.user.domain.UserConstant.ROLE_MANAGER;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ManagerAspect {
    
    @Before(value = "@annotation(com.pjt.globalmarket.user.domain.OnlyManager)")
    public void checkManager(JoinPoint joinPoint) {
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        for(Object o : args) {
            if(o instanceof UserAuthDetails) {
                UserAuthDetails user = (UserAuthDetails) o;
                if(ROLE_MANAGER.equals(user.getRole())) {
                    return;
                }
            }
        }
        // NOTE: throw 하는 것은 좋아요. 그럼, 이게 실제로 에러 코드로는 어떻게 정의되나요?
        throw new AuthenticationCredentialsNotFoundException("Manager가 아닙니다.");
    }
}

package com.pjt.globalmarket.config.interceptor;

import com.pjt.globalmarket.common.annotation.NeedLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Pre Interceptor Request UserPrincipal: {}", request.getUserPrincipal());
        NeedLogin needLogin = ((HandlerMethod)handler).getMethod().getAnnotation(NeedLogin.class);
        if(needLogin == null) {
            return true;
        }
        if(request.getUserPrincipal() == null) {
            log.warn("로그인 되지 않은 유저 입니다.");
            throw new AuthenticationCredentialsNotFoundException("로그인해야 합니다.");
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}

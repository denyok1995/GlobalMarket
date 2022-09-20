package com.pjt.globalmarket.common.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// NOTE: 보통 annotation은 별도의 패키지를 만듭니다. domain이 아닌 annotation이라는 패키지를 사용하셔도 됩니다.
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NeedLogin {
}

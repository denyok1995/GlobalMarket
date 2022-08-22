package com.pjt.globalmarket.config;

import com.pjt.globalmarket.config.auth.oauth.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    protected SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity//.authorizeRequests()
                //.antMatchers("/**/auth/**").authenticated()
                //.antMatchers("/**/manager/**").access("hasRole('ROLE_MANAGER')")
                //.anyRequest().permitAll()
                //.and()
                .formLogin().loginPage("/loginForm")
                .usernameParameter("email") // username을 앞으로 email로 사용한다.
                .loginProcessingUrl("/user/sign-in").defaultSuccessUrl("/")
                .and().logout().logoutUrl("/user/sign-out").logoutSuccessUrl("/")
                .and().oauth2Login().loginPage("/user/sign-in").userInfoEndpoint().userService(oAuth2UserService);

        return httpSecurity.build();
    }
}

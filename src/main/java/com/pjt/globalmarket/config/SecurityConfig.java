package com.pjt.globalmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    protected SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests()
                .antMatchers("/**/auth/**").authenticated()
                .antMatchers("/**/manager/**").access("hasRole('ROLE_MANAGER')")
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/loginForm")
                .usernameParameter("email") // username을 앞으로 email로 사용한다.
                .loginProcessingUrl("/user/sign-in").defaultSuccessUrl("/")
                .and().logout().logoutUrl("/user/sign-out").logoutSuccessUrl("/")
                .and().headers();

        return httpSecurity.build();
    }
}

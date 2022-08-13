package com.pjt.globalmarket.config.auth;

import com.pjt.globalmarket.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static com.pjt.globalmarket.user.domain.UserConstant.DEFAULT_PROVIDER;

public class UserAuthDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;
    private String provider;

    // 일반 사용자 로그인용 생성자
    public UserAuthDetails(User user) {
        this.user = user;
        this.provider = DEFAULT_PROVIDER;
    }

    //OAuth 사용자 로그인용 생성자
    public UserAuthDetails(User user, Map<String, Object> attributes, String provider) {
        this.user = user;
        this.attributes = attributes;
        this.provider = provider;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return user.getName();
    }

    public String getProvider() {
        return this.provider;
    }
}

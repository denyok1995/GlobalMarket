package com.pjt.globalmarket.config.auth;

import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.domain.UserGrade;
import com.pjt.globalmarket.user.domain.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class UserAuthDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    // 일반 사용자 로그인용 생성자
    public UserAuthDetails(User user) {
        this.user = user;
    }

    //OAuth 사용자 로그인용 생성자
    public UserAuthDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        // NOTE: lambda를 사용한다면, 아래 코드를 expression으로 변경할 수 있어요. 그리고, collection 이라는 변수가 필요할까요?
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return getRole().getRole();
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
        return this.user.getProvider();
    }

    public UserGrade getUserGrade() {
        return this.user.getGrade();
    }

    public UserRole getRole() {
        return this.user.getRole();
    }
}

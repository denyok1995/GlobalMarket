package com.pjt.globalmarket.config.auth.oauth;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.user.dao.UserRepository;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private static final String DEFAULT_PHONE = "010-1234-5678";

    private final BCryptPasswordEncoder encoder;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("Provider : "+provider);
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println("Attributes : "+attributes);

        OAuth2UserInfo oAuth2UserInfo = null;
        if("google".equals(provider)) {
            oAuth2UserInfo = new GoogleUserInfo(attributes);
        } else {
            throw new IllegalArgumentException("지원하지 않는 가입 양식입니다.");
        }

        Optional<User> userOptional =
                userService.getActiveUserByEmailAndProvider(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getProvider());
        User user = null;
        if(userOptional.isEmpty()) {
            System.out.println("가입되지 않은 유저입니다.");
            user = User.builder(oAuth2UserInfo.getEmail(), encoder.encode(oAuth2UserInfo.getProviderId()))
                    .name(oAuth2UserInfo.getName())
                    .phone(DEFAULT_PHONE)
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            userRepository.save(user);
        } else {
            System.out.println("이미 로그인을 진행한 유저입니다.");
            user = userOptional.get();
        }
        return new UserAuthDetails(user, attributes);
    }
}

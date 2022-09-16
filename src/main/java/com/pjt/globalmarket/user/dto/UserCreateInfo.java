package com.pjt.globalmarket.user.dto;

import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.domain.UserGrade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

import static com.pjt.globalmarket.user.domain.UserConstant.DEFAULT_PROVIDER;
import static com.pjt.globalmarket.user.domain.UserConstant.ROLE_USER;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreateInfo {

    private Long id;

    private String uuid;

    private String email;

    private String name;

    private String phone;

    @Builder.Default
    private String role = ROLE_USER;

    @Builder.Default
    private UserGrade grade = UserGrade.BRONZE;

    @Builder.Default
    private String provider = DEFAULT_PROVIDER;

    private String providerId;

    private ZonedDateTime createdAt;

    public static UserCreateInfo toDto(User user) {
        return UserCreateInfo.builder()
                .id(user.getId())
                .uuid(user.getUuid())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .providerId(user.getProviderId())
                .createdAt(user.getCreatedAt())
                .build();
    }
}

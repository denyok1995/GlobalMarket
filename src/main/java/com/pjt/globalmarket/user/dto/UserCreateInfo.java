package com.pjt.globalmarket.user.dto;

import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.domain.UserGrade;
import com.pjt.globalmarket.user.domain.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

import static com.pjt.globalmarket.user.domain.UserConstant.DEFAULT_PROVIDER;
import static com.pjt.globalmarket.user.domain.UserRole.ROLE_USER;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ApiModel(description = "생성된 사용자 정보")
public class UserCreateInfo {

    @ApiModelProperty(notes = "사용자 고유 번호", example = "1")
    private Long id;

    @ApiModelProperty(notes = "사용자 고유 번호", example = "7443ebe7-02e1-4a67-90d8-e0d52a91b1e9")
    private String uuid;

    @ApiModelProperty(notes = "사용자 아이디", example = "sa@coupang.com")
    private String email;

    @ApiModelProperty(notes = "사용자 이름", example = "홍길동")
    private String name;

    @ApiModelProperty(notes = "사용자 전화번호", example = "010-1234-5678")
    private String phone;

    @Builder.Default
    @ApiModelProperty(notes = "사용자 역할", example = "ROLE_USER")
    private UserRole role = ROLE_USER;

    @Builder.Default
    @ApiModelProperty(notes = "사용자 등급", example = "bronze")
    private UserGrade grade = UserGrade.BRONZE;

    @Builder.Default
    @ApiModelProperty(notes = "회원가입 한 경로", example = "GM")
    private String provider = DEFAULT_PROVIDER;

    @ApiModelProperty(notes = "회원가입 경로 고유번호", example = "72db238b-540d-42b0-a595-a2e7e853e0ff")
    private String providerId;

    @ApiModelProperty(notes = "생성 일자", example = "2023-09-10T10:06:53.778Z")
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

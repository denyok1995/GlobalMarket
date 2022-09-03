package com.pjt.globalmarket.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserUpdateDto {

    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자 이상 16자 미만이어야 합니다.")
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @ApiModelProperty(name = "비밀번호", notes = "사용자 비밀번호", example = "password")
    private String password;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @ApiModelProperty(name = "유저 이름", example = "홍길동")
    private String name;

    @ApiModelProperty(name = "유저 핸드폰 번호", example = "010-1234-5678")
    private String phone;
}

package com.pjt.globalmarket.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginDto {

    @ApiModelProperty(name = "아이디", notes = "로그인을 진행 할 이메일", example = "test@coupang.com")
    private String email;
    @ApiModelProperty(name = "비밀번호", notes = "로그인을 진행 할 비밀번호", example = "password")
    private String password;

}

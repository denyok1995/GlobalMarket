package com.pjt.globalmarket.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginDto {

    @ApiModelProperty(notes = "사용자 아이디", example = "sa@coupang.com")
    private String email;
    @ApiModelProperty(notes = "사용자 비밀번호", example = "password")
    private String password;

}

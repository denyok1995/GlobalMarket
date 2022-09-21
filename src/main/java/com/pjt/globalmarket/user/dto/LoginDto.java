package com.pjt.globalmarket.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "로그인 시에 필요한 정보")
public class LoginDto {

    @ApiModelProperty(notes = "사용자 아이디", example = "sa@coupang.com", required = true)
    private String email;
    @ApiModelProperty(notes = "사용자 비밀번호", example = "password", required = true)
    private String password;

}

package com.pjt.globalmarket.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "회원가입 시에 필요한 사용자 정보")
public class SignUpDto {

    @Email
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @ApiModelProperty( notes = "사용자 아이디", example = "sa@coupang.com", required = true)
    private String email;

    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자 이상 16자 미만이어야 합니다.")
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @ApiModelProperty(notes = "사용자 비밀번호", example = "password", required = true)
    private String password;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @ApiModelProperty(notes = "사용자 이름", example = "홍길동", required = true)
    private String name;

    @ApiModelProperty(notes = "사용자 번호", example = "010-1234-5678", required = false)
    private String phone;

    @ApiModelProperty(notes = "최초 회원가입 사용자 쿠폰 발급 여부", example = "true", required = false)
    private boolean welcomeCoupon;

}

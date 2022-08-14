package com.pjt.globalmarket.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpDto {

    @Email
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String email;
    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자 이상 16자 미만이어야 합니다.")
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;
    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;
    private String phone;

}

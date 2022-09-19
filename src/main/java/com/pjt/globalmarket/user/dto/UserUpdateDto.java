package com.pjt.globalmarket.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "사용자 정보 수정시에 필요한 정보")
public class UserUpdateDto {

    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자 이상 16자 미만이어야 합니다.")
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @ApiModelProperty(notes = "사용자 비밀번호", example = "password", required = true)
    private String password;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @ApiModelProperty(notes = "사용자 이름", example = "홍길동", required = true)
    private String name;

    @ApiModelProperty(notes = "사용자 핸드폰 번호", example = "010-1234-5678", required = true)
    private String phone;
}

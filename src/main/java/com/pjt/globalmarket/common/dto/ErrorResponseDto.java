package com.pjt.globalmarket.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ErrorResponseDto {

    @ApiModelProperty(notes = "Http Status Code", example = "500")
    private final HttpStatus httpStatus;

    @ApiModelProperty(notes = "에러 메시지", example = "Need To Login!!")
    private final String message;

    @Builder.Default
    @ApiModelProperty(notes = "에러 발생 시각", example = "2022-09-03T10:06:53.778Z")
    private final ZonedDateTime timestamp = ZonedDateTime.now();
}

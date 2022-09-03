package com.pjt.globalmarket.review.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.ZonedDateTime;

@Builder
@Data
public class ReviewInfo {

    @ApiModelProperty(name = "리뷰 작성한 사용자 아이디", example = "sa@coupang.com")
    private String email;

    @ApiModelProperty(name = "리뷰 내용", example = "매우 만족합니다.")
    private String content;

    @ApiModelProperty(name = "상품 점수", example = "4.8")
    private Double score;

    @ApiModelProperty(name = "도움이 된 리뷰 횟수", example = "5")
    private Integer helpNum;

    @ApiModelProperty(name = "도움이 되지 않은 리뷰 횟수", example = "1")
    private Integer noHelpNum;

    @ApiModelProperty(name = "작성된 날짜", example = "2022-09-03T10:06:53.778Z")
    private ZonedDateTime createdAt;
}

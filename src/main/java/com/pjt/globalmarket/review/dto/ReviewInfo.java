package com.pjt.globalmarket.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.ZonedDateTime;

@Builder
@Data
@ApiModel(description = "상품에 작성된 리뷰들의 정보")
public class ReviewInfo {

    @ApiModelProperty(notes = "리뷰 작성한 사용자 아이디", example = "sa@coupang.com")
    private String email;

    @ApiModelProperty(notes = "작성된 리뷰 내용", example = "매우 만족합니다.")
    private String content;

    @ApiModelProperty(notes = "상품 점수", example = "4.8")
    private Double score;

    @ApiModelProperty(notes = "해당 리뷰가 도움이 되었다고 평가 된 횟수", example = "5")
    private Integer helpNum;

    @ApiModelProperty(notes = "해당 리뷰가 도움이 되지 않았다고 평가 된 횟수", example = "1")
    private Integer noHelpNum;

    @ApiModelProperty(notes = "리뷰가 작성된 날짜", example = "2022-09-03T10:06:53.778Z")
    private ZonedDateTime createdAt;
}

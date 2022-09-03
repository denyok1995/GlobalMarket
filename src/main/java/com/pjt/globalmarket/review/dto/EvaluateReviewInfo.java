package com.pjt.globalmarket.review.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvaluateReviewInfo {

    @ApiModelProperty(name = "평가 하려는 리뷰 고유 아이디", example = "22")
    private Long reviewId;

    @ApiModelProperty(name = "평가" , example = "true")
    private Boolean isHelp;
}

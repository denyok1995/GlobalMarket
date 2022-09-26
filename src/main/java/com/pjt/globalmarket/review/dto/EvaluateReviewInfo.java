package com.pjt.globalmarket.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "상품 리뷰 도움이 되는지 안되는지 평가 시에 필요한 정보")
public class EvaluateReviewInfo {

    @ApiModelProperty(notes = "평가 하려는 리뷰 고유 아이디", example = "22", required = true)
    private long reviewId;

    @ApiModelProperty(notes = "평가" , example = "true", required = true)
    private boolean isHelp;
}

package com.pjt.globalmarket.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "리뷰 작성 시에 필요한 정보")
public class WriteReviewInfo {

    @ApiModelProperty(notes = "리뷰 작성 할 상품 고유 번호", example = "22", required = true)
    private long productId;

    @ApiModelProperty(notes = "작성한 리뷰 내용", example = "매우 만족합니다.", required = true)
    private String content;

    @ApiModelProperty(notes = "평가한 상품 점수", example = "4.8", required = true)
    private double score;

    //private MultipartFile productImg;
}

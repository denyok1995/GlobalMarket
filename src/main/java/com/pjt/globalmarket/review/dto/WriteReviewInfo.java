package com.pjt.globalmarket.review.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WriteReviewInfo {

    @ApiModelProperty(name = "리뷰 작성 할 상품 고유 번호", example = "22")
    private Long productId;

    @ApiModelProperty(name = "리뷰 내용", example = "매우 만족합니다.")
    private String content;

    @ApiModelProperty(name = "상품 점수", example = "4.8")
    private Double score;

    //private MultipartFile productImg;
}

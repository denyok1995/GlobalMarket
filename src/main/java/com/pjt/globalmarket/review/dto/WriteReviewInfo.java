package com.pjt.globalmarket.review.dto;

import lombok.Data;

@Data
public class WriteReviewInfo {

    private Long productId;

    private String content;

    private Double score;

    //private MultipartFile productImg;
}

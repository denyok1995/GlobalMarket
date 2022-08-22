package com.pjt.globalmarket.review.dto;

import lombok.*;

import java.time.ZonedDateTime;

@Builder
@Data
public class ReviewInfo {

    private String email;

    private String content;

    private Double score;

    private Integer helpNum;

    private Integer noHelpNum;

    private ZonedDateTime createdAt;
}

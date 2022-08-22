package com.pjt.globalmarket.review.dto;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public class ReviewInfo {

    private String email;

    private String content;

    private Double score;

    private Integer helpNum;

    private Integer noHelpNum;

    private ZonedDateTime createdAt;
}

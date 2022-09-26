package com.pjt.globalmarket.review.dto;

import com.pjt.globalmarket.review.domain.Review;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ApiModel(description = "생성된 리뷰 정보")
public class ReviewCreateInfo {

    @ApiModelProperty(notes = "리뷰 고유 번호", example = "2")
    private long id;

    @ApiModelProperty(notes = "리뷰 작성한 사용자 아이디", example = "sa@coupang.com")
    private String email;

    @ApiModelProperty(notes = "작성된 리뷰 내용", example = "매우 만족합니다.")
    private String content;

    @ApiModelProperty(notes = "상품 점수", example = "4.8")
    private double score;

    @ApiModelProperty(notes = "해당 리뷰가 도움이 되었다고 평가 된 횟수", example = "0")
    @Builder.Default
    private int helpNum = 0;

    @ApiModelProperty(notes = "해당 리뷰가 도움이 되지 않았다고 평가 된 횟수", example = "0")
    @Builder.Default
    private int noHelpNum = 0;

    @ApiModelProperty(notes = "리뷰가 작성된 날짜", example = "1664004425.63526")
    private ZonedDateTime createdAt;

    public static ReviewCreateInfo toDto(Review review) {
        return ReviewCreateInfo.builder()
                .id(review.getId())
                .email(review.getUser().getEmail())
                .content(review.getContent())
                .score(review.getScore())
                .createdAt(review.getCreatedAt())
                .build();
    }
}

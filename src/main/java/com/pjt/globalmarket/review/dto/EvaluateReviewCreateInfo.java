package com.pjt.globalmarket.review.dto;

import com.pjt.globalmarket.review.domain.EvaluationReview;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ApiModel(description = "생성된 리뷰 평가 정보")
public class EvaluateReviewCreateInfo {

    @ApiModelProperty(notes = "평가 고유 아이디", example = "2")
    private long id;

    @ApiModelProperty(notes = "평가 하려는 리뷰 고유 아이디", example = "22")
    private long reviewId;

    @ApiModelProperty(notes = "평가" , example = "true")
    private boolean help;

    @ApiModelProperty(notes = "평가한 사용자", example = "sa@coupang.com")
    private String userEmail;


    public static EvaluateReviewCreateInfo toDto(EvaluationReview evaluationReview) {
        return EvaluateReviewCreateInfo.builder()
                .id(evaluationReview.getId())
                .reviewId(evaluationReview.getReview().getId())
                .help(evaluationReview.isHelp())
                .userEmail(evaluationReview.getUser().getEmail())
                .build();
    }
}

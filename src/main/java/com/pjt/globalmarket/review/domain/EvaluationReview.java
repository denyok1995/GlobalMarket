package com.pjt.globalmarket.review.domain;

import com.pjt.globalmarket.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class EvaluationReview {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Review review;

    @ManyToOne
    private User user;

    private Boolean isHelp;
}

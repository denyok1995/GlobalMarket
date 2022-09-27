package com.pjt.globalmarket.review.domain;

import com.pjt.globalmarket.user.domain.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class EvaluationReview {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Review review;

    @ManyToOne
    private User user;

    private boolean help;
}

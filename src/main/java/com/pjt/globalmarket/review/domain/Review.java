package com.pjt.globalmarket.review.domain;

import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Getter
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    private String content;

    @Builder.Default
    @Setter
    private Integer helpNum = 0;

    @Builder.Default
    @Setter
    private Integer noHelpNum = 0;

    private Double score;

    @CreationTimestamp
    private ZonedDateTime createdAt;
}

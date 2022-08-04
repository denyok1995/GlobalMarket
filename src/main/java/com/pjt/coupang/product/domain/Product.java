package com.pjt.coupang.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long price;

    private String mainImg;

    private String detailImg;

    private Long stock;

    private Long score;

    private Long deliveryFee;

    private String rocketDelivery;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> category;
}

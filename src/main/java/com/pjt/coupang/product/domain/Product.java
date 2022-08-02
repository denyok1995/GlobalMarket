package com.pjt.coupang.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long price;

    private String main_img;

    private String detail_img;

    private Long stock;

    private Long score;

    private Long delivery_fee;

    private String rocket_delivery;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
}

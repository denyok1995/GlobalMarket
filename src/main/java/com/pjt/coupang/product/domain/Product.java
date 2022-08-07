package com.pjt.coupang.product.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    private String mainImg;

    private String detailImg;

    private Long stock;

    private Long score;

    private Long deliveryFee;

    private String rocketDelivery;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> category;

    public static ProductBuilder builder(String name, Long price) {
        if(name == null || price == null){
            throw  new IllegalArgumentException("필수 항목 누락");
        }
        return new ProductBuilder().name(name).price(price);
    }
}

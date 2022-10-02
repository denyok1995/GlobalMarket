package com.pjt.globalmarket.product.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private double price;

    private String mainImg;

    private String detailImg;

    @Setter
    private long stock;

    @Setter
    private double score;

    private long deliveryFee;

    private String rocketDelivery;

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Category> category = new HashSet<>();

    public static ProductBuilder builder(String name, Double price) {
        if(name == null || price == null){
            throw  new IllegalArgumentException("필수 항목 누락");
        }
        return new ProductBuilder().name(name).price(price);
    }

    public void addCategory(Category category) {
        this.category.add(category);
    }
}

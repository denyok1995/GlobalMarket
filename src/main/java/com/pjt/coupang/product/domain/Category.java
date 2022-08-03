package com.pjt.coupang.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}

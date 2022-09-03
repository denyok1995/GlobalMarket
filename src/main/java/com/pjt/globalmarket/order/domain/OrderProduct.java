package com.pjt.globalmarket.order.domain;

import com.pjt.globalmarket.product.domain.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderProduct {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;

    private Long productNum;

}

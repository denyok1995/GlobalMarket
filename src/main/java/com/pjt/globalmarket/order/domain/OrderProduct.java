package com.pjt.globalmarket.order.domain;

import com.pjt.globalmarket.product.domain.Product;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderProduct {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Product product;

    private long productNum;

}

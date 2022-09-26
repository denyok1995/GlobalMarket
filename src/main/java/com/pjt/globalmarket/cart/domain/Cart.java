package com.pjt.globalmarket.cart.domain;

import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.user.domain.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue
    private long id;

    // 장바구니에 넣은 product
    @ManyToOne
    private Product product;

    // 장바구니에 넣은 위 product의 갯수
    @Setter
    private long productNum;

    @ManyToOne
    private User user;
}

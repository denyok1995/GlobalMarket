package com.pjt.globalmarket.cart.dao;

import com.pjt.globalmarket.cart.domain.Cart;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findCartByProductAndUser(Product product, User user);

    List<Cart> findCartsByUser(User user);
}

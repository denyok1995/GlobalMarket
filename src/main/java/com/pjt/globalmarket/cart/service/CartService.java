package com.pjt.globalmarket.cart.service;

import com.pjt.globalmarket.cart.dao.CartRepository;
import com.pjt.globalmarket.cart.domain.Cart;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserService userService;
    private final ProductService productService;
    private final CartRepository cartRepository;

    @Transactional
    public void saveProductInUserCart(String email, String provider, Long productId, Long productNum) {
        Optional<Product> product = productService.getProductById(productId);

        if(product.isEmpty()) {
            throw new IllegalArgumentException("해당 상품이 존재하지 않습니다.");
        } else if(product.get().getStock() < productNum) {
            throw new IllegalArgumentException("해당 상품의 재고가 부족합니다.");
        }

        Optional<User> user = userService.getActiveUserByEmailAndProvider(email, provider);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("없는 사용자입니다.");
        }

        Optional<Cart> savedCart = cartRepository.findCartByProductAndUser(product.get(), user.get());

        if (savedCart.isPresent()) {
            savedCart.get().setProductNum(savedCart.get().getProductNum() + productNum);
        } else {
            Cart cart = Cart.builder().product(product.get())
                    .productNum(productNum)
                    .user(user.get())
                    .build();
            cartRepository.save(cart);
        }
    }

    public List<Cart> getAllProductsInCartByUser(String email, String provider) {
        Optional<User> user = userService.getActiveUserByEmailAndProvider(email, provider);
        if (user.isEmpty()) {
            return new ArrayList<>();
        }
        return cartRepository.findCartsByUser(user.get()).stream().filter(cart -> !isSoldOut(cart.getProduct(), cart.getProductNum())).collect(Collectors.toList());
    }

    public boolean isSoldOut(Product product, Long num) {
        Optional<Product> productById = productService.getProductById(product.getId());
        if(productById.isEmpty()) {
            return true;
        }
        return productById.get().getStock() < num;
    }
}

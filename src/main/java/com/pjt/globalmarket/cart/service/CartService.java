package com.pjt.globalmarket.cart.service;

import com.pjt.globalmarket.cart.dao.CartRepository;
import com.pjt.globalmarket.cart.domain.Cart;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final CartRepository cartRepository;

    @Transactional
    public void saveProductInUserCart(User user, Product product, Long productNum) {
        if(product.getStock() < productNum) {
            throw new IllegalArgumentException("해당 상품의 재고가 부족합니다.");
        }

        Optional<Cart> savedCart = cartRepository.findCartByProductAndUser(product, user);

        if (savedCart.isPresent()) {
            savedCart.get().setProductNum(savedCart.get().getProductNum() + productNum);
        } else {
            Cart cart = Cart.builder().product(product)
                    .productNum(productNum)
                    .user(user)
                    .build();
            cartRepository.save(cart);
        }
    }

    public List<Cart> getAllProductsInCartByUser(User user) {
        return cartRepository.findCartsByUser(user).stream().filter(cart -> !isSoldOut(cart.getProduct(), cart.getProductNum())).collect(Collectors.toList());
    }

    public boolean isSoldOut(Product product, Long num) {
        Optional<Product> productById = productService.getProductById(product.getId());
        if(productById.isEmpty()) {
            return true;
        }
        return productById.get().getStock() < num;
    }
}

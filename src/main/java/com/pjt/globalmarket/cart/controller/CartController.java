package com.pjt.globalmarket.cart.controller;

import com.pjt.globalmarket.cart.dto.CartDto;
import com.pjt.globalmarket.cart.service.CartService;
import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.user.domain.NeedLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @NeedLogin
    @PostMapping
    public void saveProductInCart(@AuthenticationPrincipal UserAuthDetails userAuthDetails,
                                  @RequestBody SimpleProductInfo simpleProductInfo) {
        cartService.saveProductInUserCart(userAuthDetails.getUsername(), userAuthDetails.getProvider(), simpleProductInfo.getProductId(), simpleProductInfo.getProductNum());
    }

    @GetMapping
    public List<CartDto> getAllProductInMyCart(@AuthenticationPrincipal UserAuthDetails userAuthDetails) {
        return cartService.getAllProductsInCartByUser(userAuthDetails.getUsername(), userAuthDetails.getProvider()).stream().map(cart -> {
            return CartDto.builder().id(cart.getId())
                    .productName(cart.getProduct().getName())
                    .productNum(cart.getProductNum())
                    .price(productService.getDiscountedPriceByUserGrade(userAuthDetails, cart.getProduct().getPrice()))
                    .deliveryFee(cart.getProduct().getDeliveryFee())
                    .rocketDelivery(cart.getProduct().getRocketDelivery())
                    .build();
        }).collect(Collectors.toList());
    }
}


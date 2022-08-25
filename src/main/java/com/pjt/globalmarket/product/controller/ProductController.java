package com.pjt.globalmarket.product.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.dto.ProductResponseDto;
import com.pjt.globalmarket.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Page<ProductResponseDto> allProducts(@AuthenticationPrincipal UserAuthDetails loginUser,
                                                @RequestParam int page,
                                                @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> allProducts = productService.getAllProducts(pageRequest);
        return allProducts.map(product -> ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(productService.getDiscountedPriceByUserGrade(loginUser.getUserGrade(), product.getPrice()))
                .stock(product.getStock())
                .score(product.getScore())
                .deliveryFee(product.getDeliveryFee())
                .rocketDelivery(product.getRocketDelivery())
                .categories(product.getCategory())
                .build());
    }

    @GetMapping(path = "/search")
    public Page<ProductResponseDto> searchProducts(@RequestParam String content,
                                                   @RequestParam int page,
                                                   @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> products = productService.searchProductsByContent(content, pageRequest);
        return products.map(product -> ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .score(product.getScore())
                .deliveryFee(product.getDeliveryFee())
                .rocketDelivery(product.getRocketDelivery())
                .categories(product.getCategory())
                .build());
    }
}

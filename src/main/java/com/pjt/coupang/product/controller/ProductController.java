package com.pjt.coupang.product.controller;

import com.pjt.coupang.product.domain.Product;
import com.pjt.coupang.product.dto.PageList;
import com.pjt.coupang.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Page<ProductResponseDto> allProducts(@RequestParam int page,
                                                @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> allProducts = productService.getAllProducts(pageRequest);
        Page<ProductResponseDto> products = allProducts.map(product -> ProductResponseDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .score(product.getScore())
                .deliveryFee(product.getDeliveryFee())
                .rocketDelivery(product.getRocketDelivery())
                .categories(product.getCategory())
                .build());
        return products;
    }
}

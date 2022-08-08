package com.pjt.globalmarket.product.controller;

import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.dto.ProductResponseDto;
import com.pjt.globalmarket.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/search")
    public Page<ProductResponseDto> searchProducts(@RequestParam String content,
                                                   @RequestParam int page,
                                                   @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> products = productService.searchProductsByContent(content, pageRequest);
        return products.map(product -> ProductResponseDto.builder()
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

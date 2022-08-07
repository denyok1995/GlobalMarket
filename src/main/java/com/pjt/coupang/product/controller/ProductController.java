package com.pjt.coupang.product.controller;

import com.pjt.coupang.product.domain.Product;
import com.pjt.coupang.product.dto.ProductRequestDto;
import com.pjt.coupang.product.dto.ProductResponseDto;
import com.pjt.coupang.product.service.ProductService;
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

    @PostMapping(path = "/manager/save")
    public void saveProduct(@RequestBody ProductRequestDto dto) {
        Product product = Product.builder(dto.getName(), dto.getPrice())
                .stock(dto.getStock())
                .score(dto.getScore())
                .deliveryFee(dto.getDeliveryFee())
                .rocketDelivery(dto.getRocketDelivery())
                .category(dto.getCategories())
                .build();
        productService.saveProduct(product);
    }
}

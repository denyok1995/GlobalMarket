package com.pjt.globalmarket.product.controller;

import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.dto.ProductRequestDto;
import com.pjt.globalmarket.product.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/product/manager")
public class ProductManagerController {

    private final ProductService productService;


    @PostMapping(path = "/save")
    @ApiOperation(value = "상품 저장", notes = "상품을 저장한다.")
    public void saveProduct(@RequestBody ProductRequestDto dto) {
        Product product = Product.builder(dto.getName(), dto.getPrice())
                .stock(dto.getStock())
                .score(dto.getScore())
                .deliveryFee(dto.getDeliveryFee())
                .rocketDelivery(dto.getRocketDelivery())
                .build();
        productService.saveProduct(product, dto.getCategories());
    }
}

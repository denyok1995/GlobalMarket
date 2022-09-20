package com.pjt.globalmarket.product.controller;

import com.pjt.globalmarket.common.dto.PageList;
import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.dto.ProductRequestDto;
import com.pjt.globalmarket.product.dto.ProductResponseDto;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.common.annotation.OnlyManager;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    // 특정 카테고리에 있는 제품만 목록을 표시하고 싶어요, 또는 특정 카테고리(신발)에 포함된 제품중 "나이키" 제품을 찾고 싶어요.
    // 이런 조건들은 어떻게 처리해야 할까요?
    @GetMapping(path = "/products")
    @ApiOperation(value = "상품 검색", notes = "입력(content)에 일치하는 상품을 조회한다.")
    public PageList searchProducts(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                                   @RequestParam(required = false) String content,
                                   @RequestParam int page,
                                   @RequestParam @Min(20) @Max(50) int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        PageList products;
        if(content == null) {
            Page<ProductResponseDto> paged = productService.getAllProducts(pageRequest).map(product ->
                    ProductResponseDto.toDto(product,
                            productService.getDiscountPercentByUserGrade((loginUser == null) ? null : loginUser.getUserGrade())));
            products = PageList.toDto(paged.getContent(), paged.getTotalPages(), paged.getTotalElements());
        } else {
            Page<ProductResponseDto> paged = productService.searchProductsByContent(content, pageRequest).map(product
                    -> ProductResponseDto.toDto(product,
                    productService.getDiscountPercentByUserGrade((loginUser == null) ? null : loginUser.getUserGrade())));
            products = PageList.toDto(paged.getContent(), paged.getTotalPages(), paged.getTotalElements());
        }
        return products;
    }

    @OnlyManager
    @PostMapping(path = "/product/manager/save")
    @ApiOperation(value = "상품 저장", notes = "상품을 저장한다.")
    public ProductResponseDto saveProduct(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                                          @RequestBody ProductRequestDto dto) {
        Product product = Product.builder(dto.getName(), dto.getPrice())
                .stock(dto.getStock())
                .score(dto.getScore())
                .deliveryFee(dto.getDeliveryFee())
                .rocketDelivery(dto.getRocketDelivery())
                .build();
        return ProductResponseDto.toDto(productService.saveProduct(product, dto.getCategories()), 1.0);

    }

    @GetMapping(path = "/product/{productId}")
    @ApiOperation(value = "상품 상세 조회", notes = "상품 상세 정보를 조회한다.")
    public ProductResponseDto getProduct(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                                         @PathVariable long productId) {
        Product product = productService.getProductById(productId).orElseThrow();
        return ProductResponseDto.toDto(product,
                productService.getDiscountPercentByUserGrade((loginUser == null) ? null : loginUser.getUserGrade()));
    }
}

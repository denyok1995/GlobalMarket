package com.pjt.globalmarket.product.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.dto.ProductRequestDto;
import com.pjt.globalmarket.product.dto.ProductResponseDto;
import com.pjt.globalmarket.product.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // NOTE: 만일 page=0 또는 size=1000000 와 같이 큰 숫자를 넣으면 어떻게 될까요?
    // allProduct와 search의 차이는 뭘까요?, 특정 카테고리에 있는 제품만 목록을 표시하고 싶어요, 또는 특정 카테고리(신발)에 포함된 제품중 "나이키" 제품을 찾고 싶어요.
    // 이런 조건들은 어떻게 처리해야 할까요?
    @GetMapping(path = "/products")
    @ApiOperation(value = "전체 상품 조회", notes = "Paging 처리된 모든 상품을 조회한다.")
    public Page<ProductResponseDto> allProducts(@AuthenticationPrincipal UserAuthDetails loginUser,
                                                @RequestParam int page,
                                                @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> allProducts = productService.getAllProducts(pageRequest);
        // NOTE: product에서 가격을 담는 부분이 cart와 동일하지 않나요? 전체 코드가 모드 겹치는 것 같은데??
        // 바로 아래의 search도 사실 이것과 겹칩니다. 검색했을때와 전체 상품 가격이 다르면 안될텐데요?
        // loginUser == null이 되는 경우는 없나요? (로그인 사용자가 아니면 loginUser가 어떻게 넘어오죠?)
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

    @GetMapping(path = "/products/search")
    @ApiOperation(value = "상품 검색", notes = "입력(content)에 일치하는 상품을 조회한다.")
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

    @PostMapping(path = "/product/manager/save")
    @ApiOperation(value = "상품 저장", notes = "상품을 저장한다.")
    public ProductResponseDto saveProduct(@RequestBody ProductRequestDto dto) {
        Product product = Product.builder(dto.getName(), dto.getPrice())
                .stock(dto.getStock())
                .score(dto.getScore())
                .deliveryFee(dto.getDeliveryFee())
                .rocketDelivery(dto.getRocketDelivery())
                .build();
        return ProductResponseDto.toDto(productService.saveProduct(product, dto.getCategories()));

    }

    // NOTE: 상품의 상세 조회도 필요해요.
}

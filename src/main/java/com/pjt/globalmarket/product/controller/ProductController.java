package com.pjt.globalmarket.product.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.dto.ProductResponseDto;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.user.domain.UserGrade;
import com.pjt.globalmarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;
    private static final String IS_NOT_USER = "default";
    Map<String, Double> discount = new HashMap<>();

    @PostConstruct
    public void init (){
        this.discount.put(IS_NOT_USER, 1.0); //아직 로그인 하지 않은 회원
        this.discount.put(UserGrade.BRONZE.getGrade(), 0.99 ); //1% 할인율
        this.discount.put(UserGrade.SILVER.getGrade(), 0.97 ); //3% 할인율
        this.discount.put(UserGrade.GOLD.getGrade(), 0.95 ); //5% 할인율
        this.discount.put(UserGrade.DIAMOND.getGrade(), 0.9 ); //10% 할인율
    }

    @GetMapping
    public Page<ProductResponseDto> allProducts(@AuthenticationPrincipal UserAuthDetails loginUser,
                                                @RequestParam int page,
                                                @RequestParam int size) {
        String grade = (loginUser == null) ? IS_NOT_USER : loginUser.getUserGrade().getGrade();
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> allProducts = productService.getAllProducts(pageRequest);
        Page<ProductResponseDto> products = allProducts.map(product -> ProductResponseDto.builder()
                .name(product.getName())
                .price(product.getPrice() * discount.get(grade))
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

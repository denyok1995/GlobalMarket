package com.pjt.coupang.product.controller;

import com.pjt.coupang.product.domain.Product;
import com.pjt.coupang.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<Product> allProducts(@RequestParam int page,
                                     @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productService.getAllProducts(pageRequest);
    }
}

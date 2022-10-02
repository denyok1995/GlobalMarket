package com.pjt.globalmarket.product.dto;

import com.pjt.globalmarket.product.domain.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductWithNumInfo {

    private Product product;

    private long num;
}

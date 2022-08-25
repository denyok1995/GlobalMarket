package com.pjt.globalmarket.order.service;

import com.pjt.globalmarket.address.dao.AddressRepository;
import com.pjt.globalmarket.address.domain.Address;
import com.pjt.globalmarket.order.dto.CheckInfo;
import com.pjt.globalmarket.order.dto.OrderProductInfo;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final AddressRepository addressRepository;
    private final ProductService productService;

    public Optional<CheckInfo> getOrderInfo(User user, List<SimpleProductInfo> productsInfo) {

        Optional<Address> address = addressRepository.findAddressByUserAndMain(user, true);

        Map<Long, Long> productMap = new HashMap<>();
        for(SimpleProductInfo info : productsInfo) {
            productMap.put(info.getProductId(), info.getProductNum());
        }

        List<Product> products = productService.
                findProductsByIds(productsInfo.stream().map(info -> info.getProductId()).collect(Collectors.toList()))
                .stream().filter(product -> product.getStock() >= productMap.get(product.getId())).collect(Collectors.toList());

        List<OrderProductInfo> productInfos = new ArrayList<>();
        Double totalPrice = 0.0;
        Double totalDeliveryFee = 0.0;
        for(Product product : products) {
            Double discountedPrice = productService.getDiscountedPriceByUserGrade(user.getGrade(), product.getPrice());
            Long count = productMap.get(product.getId());
            totalPrice += discountedPrice * count;
            totalDeliveryFee += product.getDeliveryFee();
            productInfos.add(OrderProductInfo.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(discountedPrice)
                    .count(count)
                    .deliveryFee(product.getDeliveryFee())
                    .rocketDelivery(product.getRocketDelivery())
                    .build());
        }

        return Optional.ofNullable(CheckInfo.builder().consumerName(user.getName())
                .consumerPhone(user.getPhone())
                .consumerAddress((address.isEmpty()) ? null : address.get().getContent())
                .orderProducts(productInfos)
                .totalDeliveryFee(totalDeliveryFee)
                .totalPrice(totalPrice)
                .build());
    }
}

package com.pjt.globalmarket.order.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.order.dto.CheckInfo;
import com.pjt.globalmarket.order.service.OrderService;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import com.pjt.globalmarket.user.domain.NeedLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @NeedLogin
    @PostMapping
    public CheckInfo getOrderInfo(@AuthenticationPrincipal UserAuthDetails loginUser,
                                  @RequestBody List<SimpleProductInfo> productsInfo) {
        Optional<CheckInfo> orderInfo = orderService.getOrderInfo(loginUser, productsInfo);
        return (orderInfo.isEmpty()) ? CheckInfo.builder().build() : orderInfo.get();
    }
}

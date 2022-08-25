package com.pjt.globalmarket.order.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.order.dto.CheckInfo;
import com.pjt.globalmarket.order.service.OrderService;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import com.pjt.globalmarket.user.domain.NeedLogin;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.service.UserService;
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
    private final UserService userService;

    @NeedLogin
    @PostMapping
    public CheckInfo getOrderInfo(@AuthenticationPrincipal UserAuthDetails loginUser,
                                  @RequestBody List<SimpleProductInfo> productsInfo) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        Optional<CheckInfo> orderInfo = orderService.getOrderInfo(user, productsInfo);
        return (orderInfo.isEmpty()) ? CheckInfo.builder().build() : orderInfo.get();
    }
}

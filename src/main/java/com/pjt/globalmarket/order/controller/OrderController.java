package com.pjt.globalmarket.order.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.coupon.domain.UserCoupon;
import com.pjt.globalmarket.coupon.service.CouponService;
import com.pjt.globalmarket.order.dto.CheckInfo;
import com.pjt.globalmarket.order.dto.OrderRequestInfo;
import com.pjt.globalmarket.order.service.OrderService;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import com.pjt.globalmarket.user.domain.NeedLogin;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CouponService couponService;

    //Restful design
    @NeedLogin
    @PostMapping
    @ApiOperation(value = "구매 정보 조회", notes = "구매 진행할 정보를 조회한다.")
    public CheckInfo getOrderInfo(@AuthenticationPrincipal UserAuthDetails loginUser,
                                  @RequestBody List<SimpleProductInfo> productsInfo) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        Optional<CheckInfo> orderInfo = orderService.getOrderInfo(user, productsInfo);
        return (orderInfo.isEmpty()) ? CheckInfo.builder().build() : orderInfo.get();
    }

    @NeedLogin
    @PostMapping(path = "/pay")
    @ApiOperation(value = "결제", notes = "상품, 주문 정보를 바탕으로 결제를 진행한다.")
    public void doOrder(@AuthenticationPrincipal UserAuthDetails loginUser,
                        @RequestBody OrderRequestInfo orderRequestInfo) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        UserCoupon userCoupon = couponService.getUserCouponById(orderRequestInfo.getCouponId()).orElse(null);
        orderService.payOrder(user, orderRequestInfo, userCoupon);
    }
}

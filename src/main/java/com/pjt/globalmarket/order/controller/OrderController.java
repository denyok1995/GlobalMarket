package com.pjt.globalmarket.order.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.coupon.domain.UserCoupon;
import com.pjt.globalmarket.coupon.service.CouponService;
import com.pjt.globalmarket.order.dto.CheckInfo;
import com.pjt.globalmarket.order.dto.OrderRequestInfo;
import com.pjt.globalmarket.order.service.OrderService;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import com.pjt.globalmarket.common.annotation.NeedLogin;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "구매 정보 조회 성공", response = CheckInfo.class)
    })
    public CheckInfo getOrderInfo(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                                  @RequestBody List<SimpleProductInfo> productsInfo) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        Optional<CheckInfo> orderInfo = orderService.getOrderInfo(user, productsInfo);
        return (orderInfo.isEmpty()) ? CheckInfo.builder().build() : orderInfo.get();
    }

    // NOTE: OrderRequestInfo에 상품 주문 정보가 List<SimpleProductInfo> orderProducts으로 들어가 있는데,
    // 이 부분이 디자인이 정말 까다로운 부분 중에 하나에요. 이정도면 충분히 좋을까, 나중에 확장은 괜찮을까? 충분히 고민이 필요해요.
    // 실제 쇼핑몰에서 결제할때 상각하면 단순하지 않습니다. 1개의 쿠폰을 어떤 상품에 적용할지, 장비구니 쿠폰은 전체 금액에 적용되고..
    // 상품에 옵션(색상이나 사이즈 등)을 변경하는 경우도 있고.
    // 이런 부분은 실제 비지니스 도메인을 반영해야 하는 부분이라, 정답을 말하기 어려워요. 사용자의 액션을 기반으로 한번 디자인이 잘 되었는지 검토해보세요.
    @NeedLogin
    @PostMapping(path = "/pay")
    @ApiOperation(value = "결제", notes = "상품, 주문 정보를 바탕으로 결제를 진행한다.")
    public void doOrder(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                        @RequestBody OrderRequestInfo orderRequestInfo) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        UserCoupon userCoupon = couponService.getUserCouponById(orderRequestInfo.getCouponId()).orElse(null);
        orderService.payOrder(user, orderRequestInfo, userCoupon);
    }
}

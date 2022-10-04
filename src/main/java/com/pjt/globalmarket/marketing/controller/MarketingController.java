package com.pjt.globalmarket.marketing.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.marketing.dto.WelcomeCouponInfo;
import com.pjt.globalmarket.marketing.dto.WelcomeCouponSetInfo;
import com.pjt.globalmarket.marketing.service.MarketingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
public class MarketingController {

    private final MarketingService marketingService;

    @PostMapping("/marketing/manager/welcome-coupon")
    public WelcomeCouponInfo saveWelcomeCoupon(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                                              @RequestBody WelcomeCouponSetInfo setInfo) {
        return WelcomeCouponInfo.toDto(marketingService.saveWelcomeCoupon(setInfo));
    }

    @GetMapping("/marketing/welcome-coupon")
    public WelcomeCouponInfo findWelcomeCoupon() {
        return WelcomeCouponInfo.toDto(marketingService.getWelcomeCoupon());
    }
}

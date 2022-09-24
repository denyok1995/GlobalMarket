package com.pjt.globalmarket.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjt.globalmarket.common.AutoInsert;
import com.pjt.globalmarket.coupon.dao.CouponRepository;
import com.pjt.globalmarket.order.dto.OrderRequestInfo;
import com.pjt.globalmarket.payment.domain.PaymentType;
import com.pjt.globalmarket.product.dao.ProductRepository;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@WithUserDetails(value = "sa@test.com", userDetailsServiceBeanName = "userAuthDetailsService"
        , setupBefore = TestExecutionEvent.TEST_EXECUTION)
@Profile("test")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private AutoInsert autoInsert;
    @Autowired
    private ProductRepository productRepository;

    private long productId;
    private long couponId;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public void init() {
        autoInsert.saveUser();
        productId = autoInsert.saveProduct();
        couponId = autoInsert.saveCoupon();
    }

    @Test
    @DisplayName("결제 테스트")
    public void get_order_info_test() throws Exception {
        List<SimpleProductInfo> productInfos = new ArrayList<>();
        SimpleProductInfo simpleProductInfo = SimpleProductInfo.builder().productId(productId).productNum(2L).build();
        productInfos.add(simpleProductInfo);
        OrderRequestInfo orderInfo = new OrderRequestInfo();
        orderInfo.setOrderProducts(productInfos);
        orderInfo.setCouponId(couponId);
        orderInfo.setPaymentType(PaymentType.CARD);
        this.mockMvc.perform(post("/order/pay").content(objectMapper.writeValueAsString(orderInfo))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

}
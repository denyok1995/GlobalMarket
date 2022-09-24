package com.pjt.globalmarket.coupon.controller;

import com.pjt.globalmarket.common.AutoInsert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@WithUserDetails(value = "sa@test.com", userDetailsServiceBeanName = "userAuthDetailsService"
        , setupBefore = TestExecutionEvent.TEST_EXECUTION)
@Profile("test")
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AutoInsert autoInsert;

    @BeforeAll
    public void init() {
        autoInsert.saveUser();
        autoInsert.saveCoupon();
    }

    @Test
    @DisplayName("발급 가능한 전체 쿠폰 조회 테스트")
    public void get_all_coupons_test() throws Exception {
        this.mockMvc.perform(get("/coupons/manager/available")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("사용자가 가지고있는 전체 쿠폰 조회 테스트")
    public void get_all_my_coupons_test() throws Exception {
        this.mockMvc.perform(get("/coupons")).andExpect(status().isOk());
    }

}
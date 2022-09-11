package com.pjt.globalmarket.coupon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjt.globalmarket.coupon.dao.CouponRepository;
import com.pjt.globalmarket.coupon.domain.Coupon;
import com.pjt.globalmarket.user.dao.UserRepository;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.domain.UserConstant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    User manager;
    Coupon coupon;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public void init() {
        manager = User.builder("sa@test.com", encoder.encode("password"))
                .phone("010-1234-5678")
                .name("테스트 이름")
                .role(UserConstant.ROLE_MANAGER)
                .build();
        userRepository.save(manager);

        coupon = Coupon.builder()
                .name("봄맞이 쿠폰")
                .minPrice(10000L)
                .discountPrice(2000L)
                .maxCouponCount(2L)
                .expirationTime(ZonedDateTime.of(2022, 12, 2, 1, 2, 2,2, ZonedDateTime.now().getZone()))
                .build();
        couponRepository.save(coupon);
    }

    @Test
    @DisplayName("발급 가능한 전체 쿠폰 조회 테스트")
    @WithUserDetails(value = "sa@test.com", userDetailsServiceBeanName = "userAuthDetailsService"
            , setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void get_all_coupons_test() throws Exception {
        this.mockMvc.perform(get("/coupon/available/manager")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("사용자가 가지고있는 전체 쿠폰 조회 테스트")
    @WithUserDetails(value = "sa@test.com", userDetailsServiceBeanName = "userAuthDetailsService"
            , setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void get_all_my_coupons_test() throws Exception {
        this.mockMvc.perform(get("/coupon")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("쿠폰 발급 테스트")
    @Deprecated
    @WithUserDetails(value = "sa@test.com", userDetailsServiceBeanName = "userAuthDetailsService"
            , setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void issue_coupon_test() throws Exception {
        Map<String, Long> body = new HashMap<>();
        body.put("couponId", coupon.getId());
        this.mockMvc.perform(post("/coupon/issue")
                .content(objectMapper.writeValueAsString(body))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

}
package com.pjt.globalmarket.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjt.globalmarket.coupon.dao.CouponRepository;
import com.pjt.globalmarket.coupon.domain.Coupon;
import com.pjt.globalmarket.order.dto.OrderRequestInfo;
import com.pjt.globalmarket.payment.domain.PaymentType;
import com.pjt.globalmarket.product.dao.ProductRepository;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import com.pjt.globalmarket.user.dao.UserRepository;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.domain.UserConstant;
import com.pjt.globalmarket.user.domain.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pjt.globalmarket.user.domain.UserConstant.DEFAULT_PROVIDER;
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
    private UserRepository userRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private ProductRepository productRepository;

    Product product;
    User user;
    Coupon coupon;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public void init() {
        Optional<User> userOptional = userRepository.findUserByEmailAndProviderAndDeletedAt("sa@test.com", DEFAULT_PROVIDER, null);
        if(userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = User.builder("sa@test.com", encoder.encode("password"))
                    .phone("010-1234-5678")
                    .name("테스트 이름")
                    .role(UserRole.ROLE_MANAGER)
                    .build();
            userRepository.save(user);
        }

        coupon = Coupon.builder()
                .name("봄맞이 쿠폰")
                .minPrice(10000L)
                .discountPrice(2000L)
                .maxCouponCount(2L)
                .expirationTime(ZonedDateTime.of(2022, 12, 2, 1, 2, 2,2, ZonedDateTime.now().getZone()))
                .build();
        couponRepository.save(coupon);

        product = Product.builder("시계", 100000.0)
                .stock(3L)
                .deliveryFee(3000L).build();
        productRepository.save(product);
    }

    @Test
    @DisplayName("결제 테스트")
    public void get_order_info_test() throws Exception {
        List<SimpleProductInfo> productInfos = new ArrayList<>();
        SimpleProductInfo simpleProductInfo = SimpleProductInfo.builder().productId(product.getId()).productNum(2L).build();
        productInfos.add(simpleProductInfo);
        OrderRequestInfo orderInfo = new OrderRequestInfo();
        orderInfo.setOrderProducts(productInfos);
        orderInfo.setCouponId(coupon.getId());
        orderInfo.setPaymentType(PaymentType.CARD);
        this.mockMvc.perform(post("/order/pay").content(objectMapper.writeValueAsString(orderInfo))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

}
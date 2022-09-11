package com.pjt.globalmarket.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjt.globalmarket.cart.dao.CartRepository;
import com.pjt.globalmarket.product.dao.ProductRepository;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import com.pjt.globalmarket.user.dao.UserRepository;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.domain.UserConstant;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.pjt.globalmarket.user.domain.UserConstant.DEFAULT_PROVIDER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@WithUserDetails(value = "sa@test.com", userDetailsServiceBeanName = "userAuthDetailsService"
        , setupBefore = TestExecutionEvent.TEST_EXECUTION)
@Profile("local")
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    ObjectMapper objectMapper = new ObjectMapper();
    Product product;
    User user;

    @BeforeAll
    public void init() {
        Optional<User> userOptional = userRepository.findUserByEmailAndProviderAndDeletedAt("sa@test.com", DEFAULT_PROVIDER, null);
        if(userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = User.builder("sa@test.com", encoder.encode("password"))
                    .phone("010-1234-5678")
                    .name("테스트 이름")
                    .role(UserConstant.ROLE_MANAGER)
                    .build();
            userRepository.save(user);
        }

        product = Product.builder("시계", 100000.0)
                .stock(3L).build();
        productRepository.save(product);
    }

    @Test
    @DisplayName("장바구니에 담기 테스트")
    public void save_products_in_my_cart_test() throws Exception {
        SimpleProductInfo productInfo = new SimpleProductInfo();
        productInfo.setProductId(product.getId());
        productInfo.setProductNum(2L);
        this.mockMvc.perform(post("/cart")
                .content(objectMapper.writeValueAsString(productInfo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 상품의 재고 이상 장바구니에 담는 경우
        productInfo.setProductNum(5L);
        this.mockMvc.perform(post("/cart")
                        .content(objectMapper.writeValueAsString(productInfo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("장바구니 조회 테스트")
    public void get_products_in_may_cart_test() throws Exception {
        this.mockMvc.perform(get("/cart")).andExpect(status().isOk());
    }

    @AfterAll
    public void delete_all() {
        productRepository.deleteAll();
        userRepository.deleteAll();
        cartRepository.deleteAll();
    }
}
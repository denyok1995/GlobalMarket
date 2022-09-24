package com.pjt.globalmarket.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjt.globalmarket.common.AutoInsert;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@WithUserDetails(value = "sa@test.com", userDetailsServiceBeanName = "userAuthDetailsService"
        , setupBefore = TestExecutionEvent.TEST_EXECUTION)
@Profile("test")
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AutoInsert autoInsert;


    private long productId;
    ObjectMapper objectMapper = new ObjectMapper();

    // NOTE: 테스트에서는 member를 사용하는 경우가 별로 없어요. 각 테스트는 state-less해야 하는데, 이건 그렇지 않거든요.
    // 또는 init에서 특정한 목적을 가진다고 정의할 것이면, 이름을 user가 아닌 정확한 명칭을 해주세요.


    // NOTE: 아래의 init() 부분이 계속 반복되는데, 이걸 통합해서 preset으로 만드는 방법이 없을까요?
    // preset의 경우 데이터가 몇십건 이상은 들어가 있는게 좋을텐데, 그렇다면 아래와 같이 코드로 안하고 YAML로 하는 방법이 있을겁니다.
    @BeforeAll
    public void init() {
        // NOTE: 테스트 데이터는 "없음"으로 시작하는 경우가 많아요. 아래와 같이 isPresent()가 들어가 있다는 것은 데이터가 이미 오염된 상황을 가정하는 거라서, 고민해보세요.
        autoInsert.saveUser();
        productId = autoInsert.saveProduct();
    }

    @Test
    @DisplayName("장바구니에 담기 테스트")
    public void save_products_in_my_cart_test() throws Exception {

        // NOTE: 사용자 로그인 처리는 어디 있을까요? 아래의 카트 데이터는 누구에게 담기는 거죠?
        // 같은 프러덕트를 2명의 사용자에게 각각 담았다고 가정하고, 해당 데이터가 정상으로 담겼을까요?
        SimpleProductInfo productInfo = new SimpleProductInfo();
        productInfo.setProductId(productId);
        productInfo.setProductNum(2L);
        this.mockMvc.perform(post("/cart")
                .content(objectMapper.writeValueAsString(productInfo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // NOTE: 담겼다는게 중요한게 아니고, 담긴 결과를 조회해서 그게 정상으로 담겼다는걸 검증하는 코드를 넣어보세요.

        // 상품의 재고 이상 장바구니에 담는 경우
        productInfo.setProductNum(100L);
        this.mockMvc.perform(post("/cart")
                        .content(objectMapper.writeValueAsString(productInfo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("장바구니 조회 테스트")
    public void get_products_in_may_cart_test() throws Exception {
        this.mockMvc.perform(get("/carts")).andExpect(status().isOk());
    }

}
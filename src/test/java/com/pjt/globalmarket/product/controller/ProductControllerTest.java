package com.pjt.globalmarket.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjt.globalmarket.common.AutoInsert;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.dto.ProductRequestDto;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.user.domain.UserGrade;
import com.pjt.globalmarket.user.domain.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class ProductControllerTest {

    @Mock
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AutoInsert autoInsert;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public void init() {
        autoInsert.saveUser();
        autoInsert.saveUser("other@coupang.com", UserRole.ROLE_USER);
    }

    @ParameterizedTest(name = "?????? \"{0}\" ?????? - ?????? ?????? ??????")
    @ValueSource(strings = {"bronze", "silver", "gold", "diamond"})
    public void get_all_product_test(String grade) {
        Map<String, Double> discount = new HashMap<>();
        List<Product> productList = new ArrayList<>();
        Page<Product> products;

        discount.put("default", 1.0); //?????? ????????? ?????? ?????? ??????
        discount.put(UserGrade.BRONZE.getGrade(), 0.99 ); //1% ?????????
        discount.put(UserGrade.SILVER.getGrade(), 0.97 ); //3% ?????????
        discount.put(UserGrade.GOLD.getGrade(), 0.95 ); //5% ?????????
        discount.put(UserGrade.DIAMOND.getGrade(), 0.9 ); //10% ?????????

        Product wallet = Product.builder("??????", 10000.0).build();
        Product watch = Product.builder("??????", 35830.2).build();
        productList.add(wallet);
        productList.add(watch);

        products = new PageImpl<>(productList);


        PageRequest pageRequest = PageRequest.of(0,1);
        when(productService.getAllProducts(pageRequest)).thenReturn(products);

        Page<Product> allProducts = productService.getAllProducts(pageRequest);
        allProducts.getContent().forEach(product -> {
            if("bronze".equals(grade)) {
                assertEquals(product.getPrice() * discount.get(grade), product.getPrice() * 0.99);
                assertNotEquals(product.getPrice() * discount.get(grade), product.getPrice());
            } else if("silver".equals(grade)) {
                assertEquals(product.getPrice() * discount.get(grade), product.getPrice() * 0.97);
                assertNotEquals(product.getPrice() * discount.get(grade), product.getPrice());
            } else if("gold".equals(grade)) {
                assertEquals(product.getPrice() * discount.get(grade), product.getPrice() * 0.95);
                assertNotEquals(product.getPrice() * discount.get(grade), product.getPrice());
            } else if("diamond".equals(grade)) {
                assertEquals(product.getPrice() * discount.get(grade), product.getPrice() * 0.9);
                assertNotEquals(product.getPrice() * discount.get(grade), product.getPrice());
            }
        });
    }

    @ParameterizedTest(name = "{0} ?????? ??????")
    @ValueSource(strings = {"??????", "??????"})
    public void search_product_test(String content) throws Exception {
        this.mockMvc.perform(get("/products").param("content", content)
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("?????? ?????? ?????????")
    @WithUserDetails(value = "sa@test.com", userDetailsServiceBeanName = "userAuthDetailsService"
            , setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void save_product_manager_test() throws Exception {
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("??????")
                .score(0)
                .stock(8)
                .deliveryFee(3000)
                .price(200000)
                .rocketDelivery("Rocket_Wow")
                .build();
        this.mockMvc.perform(post("/product/manager/save").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("?????? ?????? ????????? - manager??? ????????????")
    @WithUserDetails(value = "other@coupang.com", userDetailsServiceBeanName = "userAuthDetailsService"
            , setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void save_product_test() throws Exception {
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("??????")
                .score(0)
                .stock(8)
                .deliveryFee(3000)
                .price(200000)
                .rocketDelivery("Rocket_Wow")
                .build();
        this.mockMvc.perform(post("/product/manager/save").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isForbidden());
    }

}
package com.pjt.globalmarket.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.review.dao.ReviewRepository;
import com.pjt.globalmarket.review.domain.Review;
import com.pjt.globalmarket.review.dto.EvaluateReviewInfo;
import com.pjt.globalmarket.review.dto.WriteReviewInfo;
import com.pjt.globalmarket.user.dao.UserRepository;
import com.pjt.globalmarket.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private ReviewRepository reviewRepository;

    ObjectMapper objectMapper = new ObjectMapper();
    Product product;
    User user;

    @BeforeAll
    public void init() {
        user = User.builder("sa@test.com", encoder.encode("password"))
                .phone("010-1234-5678")
                .name("테스트 이름")
                .build();
        userRepository.save(user);
        product = Product.builder("시계", 100000.0).build();
        productService.saveProduct(product, new HashSet<>());
    }


    @Test
    @DisplayName("리뷰 작성 테스트")
    @WithUserDetails(value = "sa@test.com", userDetailsServiceBeanName = "userAuthDetailsService"
            , setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void write_review_test() throws Exception {
        WriteReviewInfo review = new WriteReviewInfo();
        review.setProductId(product.getId());
        review.setScore(4.8);
        review.setContent("좋았습니다.");
        this.mockMvc.perform(post("/review")
                        .content(objectMapper.writeValueAsString(review))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("리뷰 조회 테스트")
    public void get_review_test() throws Exception {
        this.mockMvc.perform(get("/review").param("productId", String.valueOf(product.getId())))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("리뷰 평가 테스트")
    @WithUserDetails(value = "sa@test.com", userDetailsServiceBeanName = "userAuthDetailsService"
            , setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void evaluate_review_test() throws Exception {
        Review review = Review.builder().user(user)
                .product(product)
                .score(4.8)
                .content("좋았습니다.").build();
        reviewRepository.save(review);

        EvaluateReviewInfo evaluateReviewInfo = new EvaluateReviewInfo();
        evaluateReviewInfo.setReviewId(review.getId());
        evaluateReviewInfo.setIsHelp(true);
        this.mockMvc.perform(post("/review/evaluation")
                .content(objectMapper.writeValueAsString(evaluateReviewInfo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
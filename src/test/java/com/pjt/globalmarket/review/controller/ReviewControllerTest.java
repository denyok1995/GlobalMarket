package com.pjt.globalmarket.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjt.globalmarket.common.AutoInsert;
import com.pjt.globalmarket.product.dao.ProductRepository;
import com.pjt.globalmarket.product.domain.Product;
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
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithUserDetails(value = "sa@test.com", userDetailsServiceBeanName = "userAuthDetailsService"
        , setupBefore = TestExecutionEvent.TEST_EXECUTION)
@Profile("test")
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AutoInsert autoInsert;
    @Autowired
    private ReviewRepository reviewRepository;

    ObjectMapper objectMapper = new ObjectMapper();
    private long productId;

    @BeforeAll
    public void init() {
        autoInsert.saveUser();
        productId = autoInsert.saveProduct();
    }


    @Test
    @DisplayName("리뷰 작성 테스트")
    public void write_review_test() throws Exception {
        WriteReviewInfo review = new WriteReviewInfo();
        review.setProductId(productId);
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
        this.mockMvc.perform(get("/reviews").param("productId", String.valueOf(productId)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("리뷰 평가 테스트")
    public void evaluate_review_test() throws Exception {
        User user = userRepository.findUserByEmail("sa@test.com").orElse(new User());
        Product product = productRepository.findById(productId).orElse(new Product());
        Review review = Review.builder().user(user)
                .product(product)
                .score(4.8)
                .content("좋았습니다.").build();
        reviewRepository.save(review);

        EvaluateReviewInfo evaluateReviewInfo = new EvaluateReviewInfo();
        evaluateReviewInfo.setReviewId(review.getId());
        evaluateReviewInfo.setHelp(true);
        this.mockMvc.perform(post("/review/evaluation")
                .content(objectMapper.writeValueAsString(evaluateReviewInfo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
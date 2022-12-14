package com.pjt.globalmarket.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjt.globalmarket.common.AutoInsert;
import com.pjt.globalmarket.user.dto.SignUpDto;
import com.pjt.globalmarket.user.dto.UserUpdateDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithUserDetails(value = "sa@test.com", userDetailsServiceBeanName = "userAuthDetailsService"
        , setupBefore = TestExecutionEvent.TEST_EXECUTION)
@Profile("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AutoInsert autoInsert;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public void init() {
        autoInsert.saveUser();
    }


    @Test
    @Order(0)
    @DisplayName("유저 정보 저장 테스트 - 회원가입")
    public void save_user_test() throws Exception {
        SignUpDto dto = new SignUpDto();
        dto.setEmail("test@example.com");
        dto.setName("update test");
        dto.setPassword("password");
        dto.setPhone("010-1234-1234");
        this.mockMvc.perform(post("/user/sign-up")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(1)
    @DisplayName("유저 정보 수정 테스트")
    public void update_user_test() throws Exception {
        UserUpdateDto dto = new UserUpdateDto();
        dto.setName("update test");
        dto.setPassword("password");
        dto.setPhone("010-1234-1234");
        this.mockMvc.perform(put("/user")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //수정 하면서 입력한 비밀번호가 틀린 경우
        dto.setPassword("password123");
        this.mockMvc.perform(put("/user")
                        .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(1)
    @DisplayName("아이디 중복 조회 테스트")
    public void email_duplicate_test() throws Exception {
        MvcResult mvcResult1 = this.mockMvc.perform(get("/user/{email}/id", "sa@test.com"))
                .andExpect(status().isOk())
                .andReturn();
        // 중복된 아이디가 있는 경우
        assertEquals("true" ,mvcResult1.getResponse().getContentAsString());

        // 중복된 아이디가 없는 경우
        MvcResult mvcResult2 = this.mockMvc.perform(get("/user/{email}/id", "satest@coupang.com"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("false" ,mvcResult2.getResponse().getContentAsString());
    }

    @Test
    @Order(2)
    @DisplayName("유저 회원 탈퇴 테스트")
    public void user_withDrawal_test() throws Exception {
        this.mockMvc.perform(delete("/user"))
                .andExpect(status().isOk());
    }

}
package com.keduit.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("상품 등록 페이지 권한 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    // 현재 회원 이름이 admin, role이 ADMIN인 유저 로그인된 상태로 테스트
    public void itemFormTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
                // 상품 등록 페이지에 get 요청 보냄
                .andDo(print())
                // 요청과 응답 메세지를 확인할 수 있도록 콘솔창에 출력
                .andExpect(status().isOk());
                // 응답 상태 코드가 정상인지 확인
    }

    @Test
    @DisplayName("상품 등록 페이지 일반 테스트")
    @WithMockUser(username = "user", roles = "USER")
    // 현재 회원 이름이 user, role이 USER인 유저 로그인된 상태로 테스트
    public void itemFormNotTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
                // 상품 등록 페이지에 get 요청 보냄
                .andDo(print())
                // 요청과 응답 메세지를 확인할 수 있도록 콘솔창에 출력
                .andExpect(status().isForbidden());
                // 진입 요청 시 forbidden 예외 발생하면 테스트 성공적 통과
    }

}

package com.keduit.controller;

import com.keduit.dto.MemberFormDto;
import com.keduit.entity.Member;
import com.keduit.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
// mockMvc 테스트를 위한 어노테이션
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;
    // 실제 객체와 비슷하지만 테스트에 필요한 기능만 가지는 가짜 객체
    // MockMvc 객체를 이요하면 웹 브라우저에서 요청 하는 것처럼 테스트 가능

    @Autowired
    PasswordEncoder passwordEncoder;


    public Member createMember(String email, String password){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email);
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("합정동");
        memberFormDto.setPassword(password);
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);
        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/members/login")
                        // 가입된 회원 정보로 로그인이 되는지 테스트
                        // userParameter 로 이메일을 아이디로 세팅하고 로그인 url에 요청
                        .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
        // 로그인 성공 후 인증 되면 테스트 코드 통과
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void failTest() throws Exception {
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);
        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/members/login")
                        .user(email).password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

}

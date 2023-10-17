package com.keduit.entity;

import com.keduit.dto.MemberFormDto;
import com.keduit.repository.CartRepository;
import com.keduit.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
//@TestPropertySource(locations = "classpath:application-test.assertEquals")
public class CartTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("합정동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void cartmemTest(){
        Member member = createMember();
        memberRepository.save(member);

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        em.flush();
        // Jpa는 영속성 컨텍스트에 데이터를 저장 후 트랜잭션이 끝날 때 flush()를 호출하여 데이터베이스에 반영
        em.clear();
        // 영속성 컨택스트로부터 엔티티를 조회 후 영속성 컨택스트에 엔티티가 없을 경우 데이터베이를 조회
        // 장바구니 엔티티를 가져올 때 회원 엔티티도 가져오는지 보기위해 비워줌

        Cart savedCart = cartRepository.findById(cart.getId())
                // 저장된 cart 엔티티 조회
                .orElseThrow(EntityNotFoundException::new);
        Assertions.assertEquals(savedCart.getMember().getId(), member.getId());
        // member 엔티티의 id와 savedCart에 매핑된 member 엔티티의 id를 비교
    }
}

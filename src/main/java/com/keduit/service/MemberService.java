package com.keduit.service;

import com.keduit.entity.Member;
import com.keduit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
// 롤백 하기 위해
@RequiredArgsConstructor
// 생성자 주입 용
public class MemberService implements UserDetailsService {
    // UserDetailsService 인터페이스를 상속 해 구현
    
    private final MemberRepository memberRepository;
    
    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // UserDetailsService 인터페이스의 loadUserByUsername 메소드 오버라이딩
        // UserDetails 인터페이스로 회원 정보를 담는다
        Member member = memberRepository.findByEmail(email);

        if (member == null){
            throw new UsernameNotFoundException(email);
        }
        return User.builder().username(member.getEmail()).password(member.getPassword()).roles(member.getRole().toString()).build();
        // userdetail을 구현하고있는 user객체 반환
    }
}

package com.keduit.entity;

import com.keduit.constant.Role;
import com.keduit.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity{

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 컨버팅 작업 필요 따라서 createMember 메서드 생성
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setName(memberFormDto.getName());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        // 중간 변수를 사용하는 것이 더 가독성이 좋음
//        member.setPassword(passwordEncoder.encode(memberFormDto.getPassword()));
        member.setRole(Role.ADMIN);
        return member;
    }
}

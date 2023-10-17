package com.keduit.repository;

import com.keduit.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // <>안에 엔티티 하나 id 타입 하나
    Member findByEmail(String email);
}

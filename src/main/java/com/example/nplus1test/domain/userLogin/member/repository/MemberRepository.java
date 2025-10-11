package com.example.nplus1test.domain.userLogin.member.repository;

import com.example.nplus1test.domain.userLogin.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository  extends JpaRepository<Member, Long> {

    public Member findByLoginId(String loginId);
}

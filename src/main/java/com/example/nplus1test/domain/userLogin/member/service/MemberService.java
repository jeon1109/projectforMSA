package com.example.nplus1test.domain.userLogin.member.service;


import com.example.nplus1test.domain.userLogin.common.exception.InvalidInputException;
import com.example.nplus1test.domain.userLogin.member.dto.MemberDtoRequest;
import com.example.nplus1test.domain.userLogin.member.entity.Member;
import com.example.nplus1test.domain.userLogin.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // 생성자 주입
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    /**
     * 회원가입
     */
    public String signUp(MemberDtoRequest memberDtoRequest) {
        Member member = memberRepository.findByLoginId(memberDtoRequest.getLoginId());
        if (member != null) {
            throw new InvalidInputException("loginId", "이미 등록된 ID 입니다.");
        }

        member = new Member(
                null,
                memberDtoRequest.getLoginId(),
                memberDtoRequest.getPassword(),
                memberDtoRequest.getName(),
                memberDtoRequest.getBirthDate(),
                memberDtoRequest.getGender(),
                memberDtoRequest.getEmail()
        );

        memberRepository.save(member);

        return "회원가입이 완료 되었습니다.";
    }
}
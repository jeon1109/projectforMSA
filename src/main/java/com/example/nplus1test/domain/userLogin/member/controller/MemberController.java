package com.example.nplus1test.domain.userLogin.member.controller;


import com.example.nplus1test.domain.userLogin.member.dto.MemberDtoRequest;
import com.example.nplus1test.domain.userLogin.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    // 생성자 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public String signUp(@RequestBody @Valid MemberDtoRequest memberDtoRequest) {
        return memberService.signUp(memberDtoRequest);
    }

}
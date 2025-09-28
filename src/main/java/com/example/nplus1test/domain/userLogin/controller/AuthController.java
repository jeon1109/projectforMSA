package com.example.nplus1test.domain.userLogin.controller;

import com.example.nplus1test.domain.country.dto.*;
import com.example.nplus1test.domain.country.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;
   // private final MemberService memberService;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserDto.SignupRequest req) {
        service.signup(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto.TokenResponse> login(@RequestBody @Valid UserDto.LoginRequest req) {
        return ResponseEntity.ok(service.login(req));
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserDto.TokenResponse> refresh(@RequestBody @Valid UserDto.RefreshRequest req) {
        return ResponseEntity.ok(service.refresh(req));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        service.logout(token);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/signup")
//    fun signup(@RequestBody @Valid request: SignUpRequest): ResponseEntity<String>
//
//        val encoder = BCryptPasswordEncoder()
//        val encodedPassword = encoder.encode(request.password)
//        println(encodedPassword)
//
//        memberService.register(
//                username = request.username,
//                password = encodedPassword,
//                name = request.name,
//                email = request.email,
//                address = request.address,
//                rrn = request.rrn
//        )
//
//        return ResponseEntity.ok("회원가입 성공")
//    }
}
